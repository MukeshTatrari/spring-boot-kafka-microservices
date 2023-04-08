package com.order.api.kafka;

import com.domain.api.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(OrderEvent event) {
        log.info(String.format("Order event => %s", event.toString()));

        Message<OrderEvent> message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topicName).build();
        kafkaTemplate.send(message);
    }
}
