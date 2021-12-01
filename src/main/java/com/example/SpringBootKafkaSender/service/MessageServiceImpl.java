package com.example.SpringBootKafkaSender.service;

import com.example.SpringBootKafkaSender.model.Message;
import com.example.SpringBootKafkaSender.repository.MessageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class MessageServiceImpl implements MessageService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private MessageRepository messageRepository;
    private FileHandling fileHandling;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, FileHandling fileHandling) {
        this.messageRepository = messageRepository;
        this.fileHandling = fileHandling;
    }

    /**
     * Запись сообщения в БД
     *
     */
    @Override
    @Transactional
    public void save(Message message, MultipartFile multipartFile) {
        File file = fileHandling.convertMultiPartFileToFile(multipartFile);
        log.info("Запись сообщения в бд , файла в хранилище S3");
        message.setContent(file.getName());
        messageRepository.save(message);
        if(file.delete()){
            log.info("Временный файл удален");
        }else {
            log.error("Временный файл не удален");
        }
    }


}
