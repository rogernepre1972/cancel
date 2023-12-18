package br.com.desktop.infrastructure.adapter.rest.api;

import br.com.desktop.infrastructure.adapter.rest.request.PaymentApiGeracaoBoletoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class DesktopClientApiAdapter {

    @Value("${app.cliente-api.email.url-base}")
    private String clienteApiEmailUrlBase;

    @Value("${app.cliente-api.email.uri-envio}")
    private String clienteApiEmailUriEnvio;

    @Value("${app.cliente-api.email.uri-obter-email}")
    private String clienteApiEmailUriObterEmail;

    @Autowired
    private DesktopClientApiAdapter(){

    }

    private WebClient clienteApiEmailWebClient;

    @PostConstruct
    private void setRestTemplate() {
        this.clienteApiEmailWebClient = WebClient
                .builder()
                .baseUrl(this.clienteApiEmailUrlBase)
                .defaultHeaders(httpHeaders -> httpHeaders.set("Content-Type", "application/json"))
                .build();
    }

    public Void sendToClienteApiEmail(String admId, String boleto, String campanha) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("campanha", campanha);
        params.add("codigoCliente", admId);
        var uriEmail = clienteApiEmailUriEnvio.replace("{boleto}", boleto);

        return  this.clienteApiEmailWebClient
                .post()
                .uri(uriEmail + "?campanha=" + campanha + "&codigoCliente=" + admId)
                .retrieve()
                .bodyToMono(Void.class)
                .share().block();
    }

    public  void getToClienteApiEmail() {
        this.clienteApiEmailWebClient
                .get()
                .uri(clienteApiEmailUriObterEmail)
                .retrieve()
                .bodyToMono(Void.class)
                .share().block();
    }










}
