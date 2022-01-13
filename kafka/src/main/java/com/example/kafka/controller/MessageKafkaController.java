package com.example.kafka.controller;

import com.example.kafka.model.DTOKafkaMessage;
import com.example.kafka.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sbk/kafka")
@RequiredArgsConstructor
public class MessageKafkaController {

    private final static Logger log = LogManager.getLogger(MessageKafkaController.class);
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public void receiveMessageToSend(@RequestBody DTOKafkaMessage dtoKafkaMessage) {
        log.info("Получение сообщения из sbks");
        kafkaProducerService.sendMessage(dtoKafkaMessage);
    }
}
