package com.example.sbks.service;

import com.example.sbks.model.Message;
import com.example.sbks.repository.MessageRepository;
import com.example.sbks.repository.S3Repository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;
    private final FileHandling fileHandling;
    private final S3Repository s3Repository;

/*    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, FileHandling fileHandling, S3Repository s3Repository) {
        this.messageRepository = messageRepository;
        this.fileHandling = fileHandling;
        this.s3Repository = s3Repository;
    }*/

    /**
     * Запись сообщения в БД
     */
    @Override
    @Transactional
    public void save(Message message, MultipartFile multipartFile) {
        File file = fileHandling.convertMultiPartFileToFile(multipartFile);
        log.info("Запись сообщения в бд, файла в хранилище S3");
        String fileName = file.getName();
        message.setContent(fileName);
        s3Repository.upload(file, fileName);
        messageRepository.save(message);
        try {
            Files.deleteIfExists(Path.of(fileName));
            log.info("Временный файл удален");
        } catch (IOException e) {
            log.error("Временный файл не удален");
        }
    }

    @Override
    @Transactional
    public List<Message> getAll() {

        return null;
    }
}
