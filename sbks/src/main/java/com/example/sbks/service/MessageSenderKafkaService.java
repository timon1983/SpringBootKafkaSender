package com.example.sbks.service;

import com.example.awsS3.service.ServiceS3;
import com.example.sbks.exception.NoSuchDataFileException;
import com.example.sbks.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * Cервис для отправки сообщения в модуль kafka
 */
@Service
public class MessageSenderKafkaService implements MessageSenderService {

    private final static Logger log = LogManager.getLogger(MessageSenderKafkaService.class);
    private final RestTemplate restTemplate;
    private final MessageService messageService;
    private final String url;
    private final ServiceS3 serviceS3;

    public MessageSenderKafkaService(RestTemplate restTemplate, MessageService messageService,
                                     @Value("${kafka-url}") String url, ServiceS3 serviceS3) {
        this.restTemplate = restTemplate;
        this.messageService = messageService;
        this.url = url;
        this.serviceS3 = serviceS3;
    }

    /**
     * Получение сущности Message из БД и отправка в модуль kafka
     */
    @Override
    @Transactional
    public void sendMessage(String name) throws URISyntaxException, NoSuchDataFileException {
        URI uri = new URI(url);
        messageService.getByName(name)
                .map(message -> {
                    message.setContent(getByteContent(message.getFileNameForS3()));
                    restTemplate.postForObject(uri, message, Message.class);
                    log.info("Собщение отправлено в kafka");
                    return message;
                })
                .orElseThrow(() -> {
                    log.error("Нет файла с именем {}", name);
                    throw new NoSuchDataFileException(String.format("Нет файла с именем %s", name));
                });
    }

    /**
     * Получение файла из S3 и преобразование его в массив байтов
     */
    private byte[] getByteContent(String name) {
        File file;
        try {
            file = serviceS3.getFile(name);
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            log.error("Ошибка при загрузке файла");
            throw new NoSuchDataFileException("Ошибка при загрузке файла");
        }
    }
}
