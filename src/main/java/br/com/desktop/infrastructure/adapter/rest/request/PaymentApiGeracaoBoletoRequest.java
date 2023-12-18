package br.com.desktop.infrastructure.adapter.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Getter
@Setter
public class PaymentApiGeracaoBoletoRequest {
    private String charge_id;
    private Long customer_id;
    private Long payment_method;
    private BigDecimal value;
    private LocalDate due_at;

}
