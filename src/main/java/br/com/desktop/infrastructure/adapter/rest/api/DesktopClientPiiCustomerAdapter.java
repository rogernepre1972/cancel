package br.com.desktop.infrastructure.adapter.rest.api;

import br.com.desktop.domain.model.oss.geolocalization.CustomerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class DesktopClientPiiCustomerAdapter {

    @Value("${app.pii-customer.url}")
    private String clientPiiCustomerUrlBase;

    @Value("${app.pii-customer.uris.customer}")
    private String clientPiiCustomerUriCustomer;

    @Value("${app.pii-customer.uris.geolocalization}")
    private String clientPiiCustomerUriGeolocalization;

    private WebClient clientPiiCustomerWebClient;

    @PostConstruct
    private void setRestTemplate() {
        this.clientPiiCustomerWebClient = WebClient
                .builder()
                .baseUrl(this.clientPiiCustomerUrlBase)
                .defaultHeaders(httpHeaders -> httpHeaders.set("Content-Type", "application/json"))
                .build();
    }

    public Mono<ResponseEntity<Object>> getGeolocalization(Long customerId) {

        String url = String.format("%s%s%s", clientPiiCustomerUrlBase, clientPiiCustomerUriCustomer, clientPiiCustomerUriGeolocalization);

        return clientPiiCustomerWebClient
                .get()
                .uri(url, customerId)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(CustomerData.class)
                                .map(data -> ResponseEntity.ok().body(data));
                    } else {
                        return response.bodyToMono(String.class)
                                .map(errorBody -> ResponseEntity
                                        .status(response.statusCode())
                                        .body(errorBody));
                    }
                });
    }

}
