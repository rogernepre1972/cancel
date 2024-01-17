package br.com.desktop.infrastructure.adapter.rest.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class DesktopCustomerFinanApiAdapter {
    @Value("${app.customer-finan.api.url}")
    private String customerFinanApiUrl;

    @Value("${app.customer-finan.api.uris.deleteOcasionais}")
    private String customerFinanApiUrideleteOcasionais;

    @Autowired
    private DesktopCustomerFinanApiAdapter() {}

    private WebClient customerFinanApiWebClient;

    @PostConstruct
    private void setRestTemplate() {
        this.customerFinanApiWebClient = WebClient
                .builder()
                .baseUrl(this.customerFinanApiUrl)
                .defaultHeaders(httpHeaders -> httpHeaders.set("Content-Type", "application/json"))
                .build();
    }

    public Mono<ResponseEntity<String>> deleteOcasionais(Long num) {
        return customerFinanApiWebClient
                .delete()
                .uri(this.customerFinanApiUrl.concat(this.customerFinanApiUrideleteOcasionais), num)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(String.class)
                                .map(responseBody -> ResponseEntity.ok().body(responseBody));
                    } else if (response.statusCode().is4xxClientError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.just(ResponseEntity.status(response.statusCode())
                                        .body("Ocasionais do cliente " + num + " não localizados"))
                                );
                    } else if (response.statusCode().is5xxServerError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.just(ResponseEntity.status(response.statusCode())
                                        .body("Erro "+ response.statusCode() + " na chamada para a API desktop-costumer-finan-api: " + errorBody))
                                );
                    } else {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(errorBody))
                                );
                    }
                });
    }

}
