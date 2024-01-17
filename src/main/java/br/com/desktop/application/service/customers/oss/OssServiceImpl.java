package br.com.desktop.application.service.customers.oss;

import br.com.desktop.domain.model.oss.geolocalization.Customer;
import br.com.desktop.domain.model.oss.geolocalization.CustomerData;
import br.com.desktop.domain.model.oss.availability.ScheduleParameters;
import br.com.desktop.domain.model.oss.common.Address;
import br.com.desktop.domain.model.oss.common.Coordinates;
import br.com.desktop.domain.model.oss.common.Phone;
import br.com.desktop.domain.model.oss.common.PhoneType;
import br.com.desktop.domain.model.oss.geolocalization.CustomerGeolocalization;
import br.com.desktop.domain.model.oss.serviceOrder.Requester;
import br.com.desktop.domain.model.oss.serviceOrder.ServiceOrderRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class OssServiceImpl {

    private OssServiceImpl() {
    }

    public static ScheduleParameters createScheduleParameters(CustomerData customerData,
                                                              String initialDateForCheck,
                                                              String token) {
        if (customerData == null || customerData.getData() == null || customerData.getData().getCustomer() == null ||
                customerData.getData().getCustomerGeolocalization() == null || initialDateForCheck == null ||
                initialDateForCheck.isEmpty() || token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Invalid arguments for the method createScheduleParameters.");
        }

        ScheduleParameters sp = new ScheduleParameters();
        sp.setInitialDateForCheck(initialDateForCheck);
        sp.setLongitude(customerData.getData().getCustomerGeolocalization().getLongitude());
        sp.setLatitude(customerData.getData().getCustomerGeolocalization().getLatitude());
        sp.setDocument(customerData.getData().getCustomer().getRegistryCode());
        sp.setNumber(customerData.getData().getCustomer().getAddressNumber());
        sp.setStreet(customerData.getData().getCustomer().getStreet());
        sp.setNeighborhood(customerData.getData().getCustomer().getNeighborhood());
        sp.setCity(customerData.getData().getCustomer().getCity());
        sp.setState(customerData.getData().getCustomer().getState());
        sp.setCountry(customerData.getData().getCustomer().getCountry());
        sp.setZipCode(customerData.getData().getCustomer().getZipCode());
        sp.setToken(token);

        return sp;
    }

    public static ServiceOrderRequest createServiceOrderRequest(CustomerData customerData,
                                                                String requesterName,
                                                                String requesterType,
                                                                String ossServiceOrderTypeId,
                                                                String estimatedStart) {
        if (customerData == null || customerData.getData() == null) {
            throw new IllegalArgumentException("Object CustomerData is invalid or null.");
        }

        Requester requester = new Requester(requesterName, requesterType);
        Coordinates coordinates = new Coordinates(
                customerData.getData().getCustomerGeolocalization().getLatitude(),
                customerData.getData().getCustomerGeolocalization().getLongitude()
        );

        Customer customer = customerData.getData().getCustomer();
        Address address = new Address(
                customer.getStreet(),
                customer.getAddressNumber(),
                customer.getNeighborhood(),
                customer.getCity(),
                customer.getState(),
                customer.getCountry(),
                customer.getZipCode()
        );

        PhoneType phoneType = PhoneType.fromValue(customer.getPhoneType());
        Phone phone = new Phone(customer.getPhoneNumber(), phoneType.getDescription());
        List<Phone> phones = Collections.singletonList(phone);

        return new ServiceOrderRequest.Builder()
                .withRequester(requester)
                .withCoordinates(coordinates)
                .withServiceOrderTypeId(ossServiceOrderTypeId)
                .withAddress(address)
                .withPhones(phones)
                .withEstimatedStart(estimatedStart)
                .withClientDocument(customer.getRegistryCode())
                .withClientEmail(customer.getEmail())
                .withClientName(customer.getName())
                // TODO: [ALTERAR] ForeignIdentification = protocolo de ativação (atualmente do SIS). O fluxo de cancelamento terá esse dado.
                .withForeignIdentification("VAZIO")
                .withOriginApplication("SIS")
                .withEScheduleAvailabilityType(0)
                .build();

    }

    private static CustomerData mapToCustomerData(ServiceOrderRequest input) {
        if (input == null || input.getCoordinates() == null || input.getAddress() == null || input.getPhones() == null || input.getPhones().isEmpty()) {
            throw new IllegalArgumentException("Input is invalid or null.");
        }

        CustomerData customerData = new CustomerData();
        CustomerGeolocalization geo = new CustomerGeolocalization();
        geo.setLatitude(input.getCoordinates().getLatitude());
        geo.setLongitude(input.getCoordinates().getLongitude());

        Customer customer = new Customer();
        customer.setStreet(input.getAddress().getStreet());
        customer.setAddressNumber(input.getAddress().getNumber());
        customer.setNeighborhood(input.getAddress().getNeighborhood());
        customer.setCity(input.getAddress().getCity());
        customer.setState(input.getAddress().getState());
        customer.setCountry(input.getAddress().getCountry());
        customer.setZipCode(input.getAddress().getZipCode());

        Phone firstPhone = input.getPhones().get(0);
        customer.setPhoneNumber(firstPhone.getPhoneNumber());
        customer.setPhoneType(firstPhone.getPhoneType());

        customer.setRegistryCode(input.getClientDocument());
        customer.setEmail(input.getClientEmail());
        customer.setName(input.getClientName());
        customer.setId(input.getForeignIdentification());

        customerData.data.setCustomer(customer);
        customerData.data.setCustomerGeolocalization(geo);

        return customerData;

    }

    public static Mono<CustomerData> extractCustomerData(Mono<ResponseEntity<Object>> responseMono) {
        return responseMono.flatMap(OssServiceImpl::extractCustomerData);
    }

    public static Mono<CustomerData> extractCustomerData(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            Object responseBody = response.getBody();
            if (responseBody instanceof CustomerData) {
                return Mono.just((CustomerData) responseBody);
            } else if (responseBody instanceof String) {

                try {
                    CustomerData customerData = convertJsonToCustomerData((String) responseBody);
                    return customerData != null ? Mono.just(customerData) : Mono.error(new RuntimeException("Empty or null CustomerData object"));
                } catch (Exception e) {
                    return Mono.error(new RuntimeException("JSON parsing error: " + e.getMessage()));
                }
            }
        }
        return Mono.error(new RuntimeException("Erro: " + response.getBody() + "\n" + response.getStatusCode()));
    }

    public static String extractAccessTokenSchedule(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode accessTokenNode = rootNode.path("access_token");
            return accessTokenNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomerData convertJsonToCustomerData(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ServiceOrderRequest input = mapper.readValue(json, ServiceOrderRequest.class);
            return mapToCustomerData(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Mono<ResponseEntity<T>> processResponseGeneric(Mono<ResponseEntity<T>> responseEntityMono) {

        return responseEntityMono.flatMap(response -> {
            HttpStatus statusCode = response.getStatusCode();
            if (!statusCode.is2xxSuccessful() && !statusCode.is4xxClientError() && !statusCode.is5xxServerError())
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

            return Mono.just(new ResponseEntity<>(response.getBody(), statusCode));

        }).onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    public static HttpRequest.BodyPublisher buildFormDataFromMap(Map<String, String> data) {
        var builder = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    public static URI buildUriScheduling(UriBuilder uriBuilder,
                                         ScheduleParameters params,
                                         String ossApiUrlSchedulingUriBase,
                                         String ossApiUrlSchedulingUriAvailability,
                                         String ossServiceOrderTypeId) {
        return uriBuilder
                .path(ossApiUrlSchedulingUriBase + ossApiUrlSchedulingUriAvailability)
                .queryParam("initialDateForCheck", params.getInitialDateForCheck())
                .queryParam("longitude", params.getLongitude())
                .queryParam("latitude", params.getLatitude())
                .queryParam("serviceOrderTypeId", ossServiceOrderTypeId)
                .queryParam("document", params.getDocument())
                .queryParam("number", params.getNumber())
                .queryParam("street", params.getStreet())
                .queryParam("neighborhood", params.getNeighborhood())
                .queryParam("city", params.getCity())
                .queryParam("state", params.getState())
                .queryParam("country", params.getCountry())
                .queryParam("zipCode", params.getZipCode())
                .build();
    }
}
