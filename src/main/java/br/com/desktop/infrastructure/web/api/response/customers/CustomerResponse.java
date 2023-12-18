package br.com.desktop.infrastructure.web.api.response.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.util.List;

public class CustomerResponse {
    @JsonProperty("admId")
    private long admId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("document")
    private String document;
    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("active")
    private String active;
    @JsonProperty("is_overdue")
    private Boolean is_overdue;
    @JsonProperty("negations")
    private List<Negations> negations;
    @JsonProperty("userPlans")
    private List<UserPlans> userPlans;
    @JsonProperty("billing")
    private List<Billing> billing;

}
