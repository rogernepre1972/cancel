package br.com.desktop.infrastructure.web.api.response.customers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Billing {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("bankSlipNumber")
    private String bankSlipNumber;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("dueDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private LocalDateTime dueDate;

    @JsonProperty("debtSettled")
    private String debtSettled;

    @JsonProperty("observations")
    private String observations;

    @JsonProperty("acessibleBankSlip")
    private String acessibleBankSlip;

    @JsonProperty("bank")
    private String bank;

    @JsonProperty("company")
    private String company;

    @JsonProperty("pixEmvId")
    private String pixEmvId;
}
