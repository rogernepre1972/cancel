package br.com.desktop.application.service.customers;

import br.com.desktop.application.exception.TechnicalException;
import br.com.desktop.domain.model.User;
import br.com.desktop.domain.port.UserRepository;
import br.com.desktop.infrastructure.adapter.rest.kafka.CustomerProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerProducer customerProducer;

//    private final DesktopClientApiAdapter desktopClientApiAdapter;
//    private final DesktopPaymentApiAdapter desktopPaymentApiAdapter;

    public User findByAdmId(Long admId) {
        try {
            customerProducer.sendToQueue(admId);
            return userRepository.findByAdmId(admId);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar buscar o cliente pelo admId", e);
            throw new TechnicalException("Ocorreu um erro ao tentar buscar o cliente pelo admId", e);
        }
    }
}
