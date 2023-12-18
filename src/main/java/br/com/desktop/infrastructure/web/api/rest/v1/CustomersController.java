package br.com.desktop.infrastructure.web.api.rest.v1;

import br.com.desktop.application.service.customers.CustomerServiceImpl;
import br.com.desktop.domain.model.User;
import br.com.desktop.infrastructure.adapter.rest.api.DesktopCustomerFinanApiAdapter;
import br.com.desktop.infrastructure.web.api.RestControllerUrlBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Controller
@RestController
@RequestMapping(value = RestControllerUrlBase.BASE_URL + "/v1")
public class CustomersController extends RestControllerUrlBase {

    private final CustomerServiceImpl customerService;

    private final DesktopCustomerFinanApiAdapter desktopCustomerFinanApiAdapter;

    public CustomersController(CustomerServiceImpl customerService, DesktopCustomerFinanApiAdapter desktopCustomerFinanApiAdapter) {
        this.customerService = customerService;
        this.desktopCustomerFinanApiAdapter = desktopCustomerFinanApiAdapter;
    }

    @GetMapping(path = "/customer/{admId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<ResponseEntity<User>> getClient(@PathVariable Long admId) {
        return Mono.just(new ResponseEntity<>(customerService.findByAdmId(admId), HttpStatus.OK));
    }

    @DeleteMapping(path = "/customer/ocasionais/{num}")
    public Mono<ResponseEntity<String>> deleteOcasionais(@PathVariable Long num) {
        return desktopCustomerFinanApiAdapter
                .deleteOcasionais(num)
                .flatMap(response -> {
                    HttpStatus statusCode = response.getStatusCode();
                    if (statusCode.is2xxSuccessful() || statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
                        return Mono.just(ResponseEntity.status(statusCode).body(response.getBody()));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + response.getBody()));
                    }
                })
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }
}
