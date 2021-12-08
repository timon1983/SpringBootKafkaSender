package com.example.sbks.service;

import com.example.sbks.controller.MessageController;
import com.example.sbks.model.Message;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class MessageSenderKafkaService implements MessageSenderService {

    private final static Logger log = LogManager.getLogger(MessageController.class);
    private final RestTemplate restTemplate;

    @Override
    public void sendMessage(Message message) throws URISyntaxException {
        String url = "http://localhost:8085/api/sdk/kafka/send";
        URI uri = new URI(url);
        HttpEntity<Message> messageRequest = new HttpEntity<>(message);
        restTemplate.postForObject(uri, messageRequest, Message.class);
        log.info("Отправка сообщения в Кафка");
    }
}
