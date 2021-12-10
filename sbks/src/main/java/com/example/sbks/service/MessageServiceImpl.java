package com.example.sbks.service;

import com.example.awsS3.configuration.S3Configurer;
import com.example.awsS3.repository.RepositoryS3Impl;
import com.example.awsS3.service.ServiceS3;
import com.example.awsS3.service.ServicesS3Impl;
import com.example.sbks.dto.DownloadClientInfo;
import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import com.example.sbks.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
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
    private final ServiceS3 serviceS3 = new ServicesS3Impl();

    @Override
    @Transactional
    public Message save(Message message){
        File file = new File(message.getFileNameForS3());
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(message.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        serviceS3.upload(file);
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
    public Message deleteById(Long id) {
        Message message = messageRepository.findById(id).orElse(new Message());
        message.setStatus(Status.DELETED);
        message.setDateOfDelete(LocalDateTime.now().withNano(0));
        log.info("Service.Удаление данных файла из БД по его ID={}", id);
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public Optional<Message> getById(DownloadClientInfo downloadClientInfo) {
        log.info("Service.Получение информации о файле по его id={}", downloadClientInfo.getIdFile());
        return messageRepository.findById(downloadClientInfo.getIdFile());
    }

    @Override
    @Transactional
    public Optional<Message> getByName(String name) {
        log.info("Service.Получение информации о файле по его name={}", name);
        return messageRepository.findByOriginFileName(name);
    }
}
