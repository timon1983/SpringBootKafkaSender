package com.example.SpringBootKafkaSender.service;

import com.example.SpringBootKafkaSender.model.Message;
import com.example.SpringBootKafkaSender.repository.MessageRepository;
import com.example.SpringBootKafkaSender.repository.S3Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;
    private final FileHandling fileHandling;
    private final S3Repository s3Repository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, FileHandling fileHandling, S3Repository s3Repository) {
        this.messageRepository = messageRepository;
        this.fileHandling = fileHandling;
        this.s3Repository = s3Repository;
    }

    /**
     * Запись сообщения в БД
     */
    @Override
    @Transactional
    public void save(Message message, MultipartFile multipartFile) {
        File file = fileHandling.convertMultiPartFileToFile(multipartFile);
        log.info("Запись сообщения в бд , файла в хранилище S3");
        message.setContent(file.getName());
        s3Repository.upload(file, file.getName());
        messageRepository.save(message);
        if (file.delete()) {
            log.info("Временный файл удален");
        } else {
            log.error("Временный файл не удален");
        }
    }

    @Override
    @Transactional
    public List<Message> getAll() {

        return null;
    }
}
