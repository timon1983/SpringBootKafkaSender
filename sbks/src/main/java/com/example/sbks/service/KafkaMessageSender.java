package com.example.sbks.service;

import com.example.kafka.model.DTOKafkaMessage;
import com.example.kafka.service.KafkaProducer;
import com.example.sbks.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageSender implements MessageSender{

    private final KafkaProducer kafkaProducer;
    @Value("${spring.kafka.template.default-topic}")
    private final String topic;

    @Override
    public void send(Message message) {

    }

    public DTOKafkaMessage getDTOKafkaMessage(Message message){
        return DTOKafkaMessage.builder()
                .title(message.getTitle())
                .size(message.getSize())
                .author(message.getAuthor())
                .dateOfCreate(message.getDateOfCreate())
                .timeOfCreate(message.getTimeOfCreate())
                .originFileName(message.getOriginFileName())
                .fileNameForS3(message.getFileNameForS3())
                .contentType(message.getContentType())
                .build();
    }
}
