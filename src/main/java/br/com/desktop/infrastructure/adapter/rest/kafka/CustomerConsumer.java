package br.com.desktop.infrastructure.adapter.rest.kafka;

import br.com.desktop.application.service.customers.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerConsumer {

    @Autowired
    CustomerServiceImpl customerService;

    @KafkaListener(
            topics = "${app.kafka.topics.customer_id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(Long admId) {
        System.out.println("Recebido ADM da fila:" + admId);
    }
}
