package br.com.desktop.infrastructure.adapter.rest.api;

import br.com.desktop.domain.model.oss.geolocalization.CustomerData;
import br.com.desktop.domain.model.oss.availability.AvailabilityResponse;
import br.com.desktop.domain.model.oss.availability.ErrorResponse;
import br.com.desktop.domain.model.oss.availability.ScheduleParameters;
import br.com.desktop.domain.model.oss.serviceOrder.Requester;
import br.com.desktop.domain.model.oss.serviceOrder.ServiceOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;

import static br.com.desktop.application.service.customers.oss.OssServiceImpl.*;

@Component
@Slf4j
public class DesktopOssApiAdapter {
    @Value("${app.oss-api.api-desprov.url}")
    private String ossApiUrlDesprov;

    @Value("${app.oss-api.api-desprov.uris.delete-plan}")
    private String ossApiUriDeletePlan;

    @Value("${app.oss-api.auth.grant-type}")
    private String ossApiAuthGrantType;

    @Value("${app.oss-api.api-scheduling.url}")
    private String ossApiUrlScheduling;

    @Value("${app.oss-api.api-scheduling.uri-base}")
    public String ossApiUrlSchedulingUriBase;

    @Value("${app.oss-api.api-scheduling.service-order-type}")
    private String ossServiceOrderTypeId;

    @Value("${app.oss-api.api-scheduling.uris.availability}")
    private String ossApiUrlSchedulingUriAvailability;

    @Value("${app.oss-api.api-scheduling.uris.serviceOrders}")
    public String ossApiUrlSchedulingUrisServiceOrders;

    @Value("${app.oss-api.api-scheduling.token.uri}")
    private String ossApiUrlSchedulingTokenUri;

    @Value("${app.oss-api.api-scheduling.token.client-id}")
    private String ossApiUrlSchedulingTokenClientId;

    @Value("${app.oss-api.api-scheduling.token.client-secret}")
    private String ossApiUrlSchedulingTokenClientSecret;

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String APPLICATION_JSON = "application/json";

    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER = "Bearer ";

    private DesktopOssApiAdapter() {
    }

    private WebClient ossApiWebClientDesprov;
    private WebClient ossApiWebClientScheduling;

    @PostConstruct
    private void setRestTemplate() {

        this.ossApiWebClientDesprov = WebClient
                .builder()
                .baseUrl(this.ossApiUrlDesprov)
                .defaultHeaders(httpHeaders -> httpHeaders.set(CONTENT_TYPE, APPLICATION_JSON))
                .build();

        this.ossApiWebClientScheduling = WebClient
                .builder()
                .baseUrl(this.ossApiUrlScheduling)
                .defaultHeaders(httpHeaders -> httpHeaders.set(CONTENT_TYPE, APPLICATION_JSON))
                .build();
    }

    public Mono<ResponseEntity<String>> deletePlan(Long puId) {

        return ossApiWebClientDesprov
                .delete()
                .uri(this.ossApiUrlDesprov.concat(this.ossApiUriDeletePlan), puId)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(String.class)
                                .map(responseBody -> ResponseEntity.ok().body(responseBody));
                    } else if (response.statusCode().is4xxClientError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.just(ResponseEntity.status(response.statusCode())
                                        .body("ID do plano: " + puId + " não localizados"))
                                );
                    } else if (response.statusCode().is5xxServerError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.just(ResponseEntity.status(response.statusCode())
                                        .body("Erro " + response.statusCode() + " na chamada para a API oss-api: " + errorBody))
                                );
                    } else {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(errorBody))
                                );
                    }
                });
    }

    public String requestTokenScheduling() throws InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.ossApiUrlScheduling.concat(this.ossApiUrlSchedulingTokenUri)))
                    .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED)
                    .POST(buildFormDataFromMap(Map.of(
                            "grant_type", this.ossApiAuthGrantType,
                            "client_id", this.ossApiUrlSchedulingTokenClientId,
                            "client_secret", this.ossApiUrlSchedulingTokenClientSecret)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | IOException e) {
            throw new InterruptedException("Error in token request: ".concat(e.getMessage()));
        }
    }

    public Mono<ResponseEntity<Object>> getNearestSchedules(ScheduleParameters params) {
        return this.ossApiWebClientScheduling.get()
                .uri(uriBuilder -> buildUriScheduling(uriBuilder,
                        params,
                        ossApiUrlSchedulingUriBase,
                        ossApiUrlSchedulingUriAvailability,
                        ossServiceOrderTypeId))
                .header(AUTHORIZATION, BEARER + params.getToken())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(AvailabilityResponse.class)
                                .flatMap(availabilityResponse -> {
                                    if (availabilityResponse.getData() == null || availabilityResponse.getData().isEmpty()) {
                                        // Tramento necessário pelo motivo de que em algumas situações o objeto "data" chega vazio
                                        ErrorResponse errorResponse = new ErrorResponse(false, Collections.singletonList("Erro: Não há horários disponíveis."));
                                        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
                                    } else {
                                        return Mono.just(ResponseEntity.ok(availabilityResponse));
                                    }
                                });
                    } else if(response.statusCode().is4xxClientError()) {
                        return response.bodyToMono(ErrorResponse.class)
                                .map(errorResponse -> ResponseEntity.status(response.statusCode()).body(errorResponse));
                    } else {
                        return response.bodyToMono(String.class)
                                .map(body -> ResponseEntity.status(response.statusCode()).body(body));
                    }
                });
    }

    public Mono<Object> serviceOrders(CustomerData customerData,
                                      Requester requester,
                                      String estimatedStart,
                                      String token) {

        ServiceOrderRequest request = createServiceOrderRequest(customerData,
                requester.getName(),
                requester.getType(),
                ossServiceOrderTypeId,
                estimatedStart);

        return request != null ? ossApiWebClientScheduling.post()
                .uri(ossApiUrlSchedulingUriBase + ossApiUrlSchedulingUrisServiceOrders)
                .header(AUTHORIZATION, BEARER + token)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .bodyValue(request)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(String.class);
                    } else {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)));
                    }
                }) : Mono.error(new RuntimeException("Object ServiceOrderRequest is invalid or null."));
    }

}
