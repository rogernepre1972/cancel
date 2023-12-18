package br.com.desktop.infrastructure.adapter.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClienteApiEnvioEmailRequest {
    private String boleto;
    private String to;
    private Long campanha;
    private String tiposEventos;
}
