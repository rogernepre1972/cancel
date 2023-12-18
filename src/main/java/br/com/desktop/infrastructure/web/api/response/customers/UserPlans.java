package br.com.desktop.infrastructure.web.api.response.customers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPlans {
    @JsonProperty("id")
    private long id;
    @JsonProperty("plan")
    private String plan;
    @JsonProperty("is_active")
    private String is_active;


}
