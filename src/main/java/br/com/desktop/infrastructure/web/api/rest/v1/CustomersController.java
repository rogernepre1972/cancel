package br.com.desktop.infrastructure.web.api.rest.v1;

import br.com.desktop.application.service.customers.CustomerServiceImpl;
import br.com.desktop.domain.model.mongo.PaymentCancellation;
import br.com.desktop.domain.model.oss.serviceOrder.Requester;
import br.com.desktop.domain.model.oss.availability.ScheduleParameters;
import br.com.desktop.infrastructure.adapter.rest.api.DesktopClientPiiCustomerAdapter;
import br.com.desktop.infrastructure.adapter.rest.api.DesktopCustomerFinanApiAdapter;
import br.com.desktop.infrastructure.adapter.rest.api.DesktopOssApiAdapter;
import br.com.desktop.infrastructure.web.api.RestControllerUrlBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.desktop.application.service.customers.oss.OssServiceImpl.*;


@Controller
@RestController
@RequestMapping(value = RestControllerUrlBase.BASE_URL + "/v1")
public class CustomersController extends RestControllerUrlBase {

    private final CustomerServiceImpl customerService;

    private final DesktopCustomerFinanApiAdapter desktopCustomerFinanApiAdapter;

    private final DesktopClientPiiCustomerAdapter desktopClientPiiCustomerAdapter;

    private final DesktopOssApiAdapter desktopOssApiAdapter;

    public CustomersController(CustomerServiceImpl customerService, DesktopCustomerFinanApiAdapter desktopCustomerFinanApiAdapter, DesktopClientPiiCustomerAdapter desktopClientPiiCustomerAdapter, DesktopOssApiAdapter desktopOssApiAdapter) {
        this.customerService = customerService;
        this.desktopCustomerFinanApiAdapter = desktopCustomerFinanApiAdapter;
        this.desktopClientPiiCustomerAdapter = desktopClientPiiCustomerAdapter;
        this.desktopOssApiAdapter = desktopOssApiAdapter;
    }

    @GetMapping(path = "/customer/{puId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<PaymentCancellation>> getClient(@PathVariable Long puId) {
        return Mono.just(new ResponseEntity<>(customerService.findByAdmId(puId), HttpStatus.OK));
    }

    @DeleteMapping(path = "/customer/ocasionais/{num}")
    public Mono<ResponseEntity<String>> deleteOcasionais(@PathVariable Long num) {
        Mono<ResponseEntity<String>> responseEntityMono = desktopCustomerFinanApiAdapter
                .deleteOcasionais(num)
                .map(response -> new ResponseEntity<>(response.getBody(), response.getStatusCode()));

        return processResponseGeneric(responseEntityMono).map(response ->
                new ResponseEntity<>(Objects.requireNonNull(response.getBody()), response.getStatusCode()));
    }

    // Endpoint chamando apenas a geolocalização
    @GetMapping(path = "/customer/geolocalizationOnly/{customerId}")
    public Mono<ResponseEntity<Object>> getGeolocalizationOnly(@PathVariable long customerId) {
        return processResponseGeneric(desktopClientPiiCustomerAdapter.getGeolocalization(customerId));
    }

    // Endpoint chamando a abertura de ordem de serviço depois de pegar os dados de geolocalização
    @GetMapping(path = "/customer/startServiceOrder/{customerId}")
    public Mono<ResponseEntity<Object>> startServiceOrder(
            @PathVariable long customerId,
            @RequestParam String token,
            @RequestParam String estimatedStart,
            @RequestParam String requesterName,
            @RequestParam(required = false, defaultValue = "Employee") String requesterType) {

        return desktopClientPiiCustomerAdapter.getGeolocalization(customerId)
                .flatMap(response -> extractCustomerData(response)

                        .flatMap(customerData -> desktopOssApiAdapter.serviceOrders(customerData, new Requester(requesterName, requesterType), estimatedStart, token)
                                .map(serviceOrderResponse -> ResponseEntity.ok().body(serviceOrderResponse))
                                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())))))

                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }


    @GetMapping(path = "/customer/availabilityByCustomerId/{customerId}")
    public Mono<ResponseEntity<Object>> getAvailabilityByCustomerId(@PathVariable int customerId,
                                                                    @RequestParam String initialDateForCheck) throws InterruptedException {
        String token = desktopOssApiAdapter.requestTokenScheduling();

        Mono<ResponseEntity<Object>> responseGeolocalization = getGeolocalizationOnly(customerId);
        return responseGeolocalization.flatMap(response -> {
                    if (response.getStatusCode().equals(HttpStatus.OK)) {
                        return extractCustomerData(Mono.just(response))
                                .flatMap(customerData -> {
                                    ScheduleParameters sp = createScheduleParameters(customerData, initialDateForCheck, extractAccessTokenSchedule(token));
                                    return desktopOssApiAdapter.getNearestSchedules(sp)
                                            .map(responseEntity -> responseEntity);
                                });
                    } else {
                        return Mono.just(new ResponseEntity<>(response.getBody(), response.getStatusCode()));
                    }
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("Error: " + e.getMessage())));
    }

    @GetMapping(path = "/customer/availabilityOnly/{initialDateForCheck}")
    public Mono<ResponseEntity<Object>> getAvailabilityOnly(@PathVariable String initialDateForCheck,
                                                            @RequestParam Double latitude,
                                                            @RequestParam Double longitude,
                                                            @RequestParam String document,
                                                            @RequestParam String number,
                                                            @RequestParam String street,
                                                            @RequestParam String neighborhood,
                                                            @RequestParam String city,
                                                            @RequestParam String state,
                                                            @RequestParam String contry,
                                                            @RequestParam String zipCode) throws InterruptedException {
        String token = desktopOssApiAdapter.requestTokenScheduling();
        ScheduleParameters sp = new ScheduleParameters();
        if (!Objects.isNull(token)) {
            sp.setInitialDateForCheck(initialDateForCheck);
            sp.setLatitude(latitude);
            sp.setLongitude(longitude);
            sp.setDocument(document);
            sp.setNumber(number);
            sp.setStreet(street);
            sp.setNeighborhood(neighborhood);
            sp.setCity(city);
            sp.setState(state);
            sp.setCountry(contry);
            sp.setZipCode(zipCode);
            sp.setToken(extractAccessTokenSchedule(token));
        }

        return desktopOssApiAdapter.getNearestSchedules(sp)
                .map(responseEntity -> responseEntity);

    }


}
