package br.com.desktop.infrastructure.adapter.rest.api;

import br.com.desktop.infrastructure.adapter.rest.request.PaymentApiGeracaoBoletoRequest;
import br.com.desktop.infrastructure.adapter.rest.response.PaymentApiRegisterValidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@Component
public class DesktopPaymentApiAdapter {

    @Value("${app.payment-api.boleto.url-base}")
    private String paymentApiBoletoUrlBase;

    @Value("${app.payment-api.boleto.uri-boleto}")
    private String paymentApiBoletoUriBoleto;

    @Value("${app.payment-api.boleto.uri-register-validate}")
    private String paymentApiRegisterValidate;

    @Autowired
    private DesktopPaymentApiAdapter(){

    }

    private  WebClient paymentApiBoletoWebClient;


    @PostConstruct
    public void setRestTemplate() {
        this.paymentApiBoletoWebClient = WebClient
                .builder()
                .baseUrl(this.paymentApiBoletoUrlBase)
                .defaultHeaders(httpHeaders -> httpHeaders.set("Content-Type", "application/json"))
                .build();
    }

    public Void sendToPaymentApiBoleto(PaymentApiGeracaoBoletoRequest request) {
        return  this.paymentApiBoletoWebClient
                .post()
                .uri(paymentApiBoletoUriBoleto)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .share().block();
    }

    public PaymentApiRegisterValidateResponse registerValidate(String chargeId) {
        var uriRegisterValidate = paymentApiRegisterValidate.replace("{chargeId}", chargeId);

        return  this.paymentApiBoletoWebClient
                .get()
                .uri(uriRegisterValidate)
                .retrieve()
                .bodyToMono(PaymentApiRegisterValidateResponse.class)
                .share().block();
    }


}
