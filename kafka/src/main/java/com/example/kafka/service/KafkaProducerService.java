package com.example.kafka.service;

import com.example.kafka.model.DTOKafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${spring.kafka.template.default-topic}")
    private String topic;


    public void sendMessage(DTOKafkaMessage dtoKafkaMessage){
        kafkaTemplate.send(topic, dtoKafkaMessage);
    }
}
