package com.example.sbks.service;

import com.example.sbks.exception.NoSuchDataFileException;
import com.example.sbks.model.Message;
import com.example.sbks.repository.MessageRepository;
import com.example.sbks.repository.RepositoryS3;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@RequiredArgsConstructor
@PropertySource("classpath:values.properties")
public class MessageSenderKafkaService implements MessageSenderService {

    private final static Logger log = LogManager.getLogger(MessageSenderKafkaService.class);
    private final RestTemplate restTemplate;
    private final MessageRepository messageRepository;
    @Value("${kafka-url}")
    private String url;
    private final RepositoryS3 repositoryS3;

    /**
     * Получение сущности Message из БД и отправка в модуль kafka
     */
    @Override
    @Transactional
    public void sendMessage(String name) throws URISyntaxException, NoSuchDataFileException {
        URI uri = new URI(url);
        messageRepository.findByOriginFileName(name)
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
            file = repositoryS3.download(name);
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            log.error("Ошибка при загрузке файла");
            throw new NoSuchDataFileException("Ошибка при загрузке файла");
        }
    }
}
