package br.com.desktop.infrastructure.adapter.rest.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerProducer {
    @Value("${app.kafka.topics.customer_id}")
    private String customerIdTopic;

    private KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    public CustomerProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToQueue(Long admId) {
        kafkaTemplate.send(customerIdTopic, admId);
    }
}


