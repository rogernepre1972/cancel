package br.com.desktop.infrastructure.web.api.response.customers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Negations {
   @JsonProperty("platarform")
    private String plataform;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("inclusionDate")
    private LocalDateTime inclusionDate;
    @JsonProperty("amount")
    private double amount;
}
