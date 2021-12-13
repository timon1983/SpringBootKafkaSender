package com.example.sbks.service;

import com.example.awsS3.service.ServiceS3;
import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import com.example.sbks.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;
    private final ServiceS3 serviceS3;

    @Override
    @Transactional
    public Message save(Message message) {
        File file = new File(message.getFileNameForS3());
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(message.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        serviceS3.upload(file);
        message.setStatus(Status.UPLOAD);
        log.info("Service.Запись сообщения в бд");
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public List<Message> getAll() {
        log.info("Service.Получение списка всех файлов");
        return messageRepository.findAllByStatus(Status.UPLOAD);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            message.get().setStatus(Status.DELETED);
            message.get().setDateOfDelete(LocalDateTime.now().withNano(0));
            log.info("Service.Удаление данных файла из БД по его ID={}", id);
            messageRepository.save(message.get());
        }
    }

    @Override
    @Transactional
    public Optional<Message> getById(Long id) {
        log.info("Service.Получение информации о файле по его id={}", id);
        return messageRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Message> getByName(String name) {
        log.info("Service.Получение информации о файле по его name={}", name);
        return messageRepository.findByOriginFileName(name);
    }
}
