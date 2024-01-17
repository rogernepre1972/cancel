package br.com.desktop.infrastructure.web.api;

import br.com.desktop.application.service.customers.CustomerServiceImpl;
import br.com.desktop.domain.model.mongo.PaymentCancellation;
import br.com.desktop.infrastructure.adapter.rest.api.DesktopClientPiiCustomerAdapter;
import br.com.desktop.infrastructure.adapter.rest.api.DesktopCustomerFinanApiAdapter;
import br.com.desktop.infrastructure.adapter.rest.api.DesktopOssApiAdapter;
import br.com.desktop.infrastructure.web.api.rest.v1.CustomersController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class CustomersControllerTest {

    @InjectMocks
    private CustomersController customersController;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private DesktopCustomerFinanApiAdapter desktopCustomerFinanApiAdapter;

    @Mock
    private DesktopClientPiiCustomerAdapter desktopClientPiiCustomerAdapter;

    @Mock
    private DesktopOssApiAdapter desktopOssApiAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClient() {
        Long puId = 1L;
        PaymentCancellation paymentCancellation = new PaymentCancellation();
        when(customerService.findByAdmId(puId)).thenReturn(paymentCancellation);

        Mono<ResponseEntity<PaymentCancellation>> result = customersController.getClient(puId);

        StepVerifier.create(result)
                .expectNext(new ResponseEntity<>(paymentCancellation, HttpStatus.OK))
                .verifyComplete();
    }

    @Test
    void testDeleteOcasionais() {
        Long num = 1L;
        String responseBody = "Success";
        when(desktopCustomerFinanApiAdapter.deleteOcasionais(num))
                .thenReturn(Mono.just(new ResponseEntity<>(responseBody, HttpStatus.OK)));

        Mono<ResponseEntity<String>> result = customersController.deleteOcasionais(num);

        StepVerifier.create(result)
                .expectNext(new ResponseEntity<>(responseBody, HttpStatus.OK))
                .verifyComplete();
    }

    @Test
    void testGetGeolocalizationOnly() {
        long customerId = 1L;
        Object geoResponse = new Object();
        when(desktopClientPiiCustomerAdapter.getGeolocalization(customerId))
                .thenReturn(Mono.just(new ResponseEntity<>(geoResponse, HttpStatus.OK)));

        Mono<ResponseEntity<Object>> result = customersController.getGeolocalizationOnly(customerId);

        StepVerifier.create(result)
                .expectNext(new ResponseEntity<>(geoResponse, HttpStatus.OK))
                .verifyComplete();
    }

    @Test
    void testStartServiceOrder() {
        // escrever
    }

    @Test
    void testGetAvailabilityByCustomerId() {
        // escrever
    }

    @Test
    void testGetAvailabilityOnly() {
        // escrever
    }
}
