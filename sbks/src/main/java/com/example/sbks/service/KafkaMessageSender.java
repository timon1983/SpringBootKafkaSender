package com.example.sbks.service;

import com.example.sbks.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageSender implements MessageSender{

    private final KafkaTemplate kafkaTemplate;
    @Value("${spring.kafka.template.default-topic}")
    private final String topic;

    @Override
    public void send(Message message) {
        kafkaTemplate.send(topic, message);
    }
}
