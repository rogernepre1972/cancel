package br.com.desktop.application.service.customers;

import br.com.desktop.application.exception.TechnicalException;
import br.com.desktop.domain.model.mongo.PaymentCancellation;
import br.com.desktop.domain.port.mongo.PaymentRepository;
import br.com.desktop.infrastructure.adapter.rest.kafka.CustomerProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
public class CustomerServiceImpl {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CustomerProducer customerProducer;

//    private final DesktopClientApiAdapter desktopClientApiAdapter;
//    private final DesktopPaymentApiAdapter desktopPaymentApiAdapter;

    public PaymentCancellation findByAdmId(Long puId) {
        try {
            customerProducer.sendToQueue(puId);
            //PaymentCancellation paymentCancellation = paymentRepository.findByPuId(puId).get();
            PaymentCancellation paymentCancellation = saveMongoTest();
            return paymentCancellation;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar buscar o cliente pelo admId", e);
            throw new TechnicalException("Ocorreu um erro ao tentar buscar o cliente pelo admId", e);
        }
    }

    public PaymentCancellation saveMongoTest(){
        PaymentCancellation paymentCancellation = new PaymentCancellation();
        paymentCancellation.setCustomerId(469055L);
        paymentCancellation.setPuId(12345L);
        paymentCancellation.setCreatedAt(LocalDateTime.now());
        paymentCancellation.setUpdatedAt(LocalDateTime.now());
        paymentCancellation.setStatus(PaymentCancellation.Status.RECEIVED_MAILING);
        paymentCancellation.setHasError(Boolean.FALSE);
        paymentRepository.save(paymentCancellation);
        return paymentCancellation;
    }

}
