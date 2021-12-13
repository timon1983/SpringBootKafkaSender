package com.example.sbks.service;

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

    private final static Logger log = LogManager.getLogger(MessageSenderKafkaService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendMessage(Message message) throws URISyntaxException {
        URI uri = new URI("http://localhost:8085/api/sdk/kafka/send");
        HttpEntity<Message> messageRequest = new HttpEntity<>(message);
        restTemplate.postForObject(uri, messageRequest, Message.class);
        log.info("Отправка сообщения в Кафка");
    }
}
