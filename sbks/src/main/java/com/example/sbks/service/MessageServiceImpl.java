package com.example.sbks.service;

import com.example.sbks.model.Message;
import com.example.sbks.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;


    /**
     * Запись сообщения в БД
     */
    @Override
    @Transactional
    public Message save(Message message) {

        log.info("Запись сообщения в бд");
        return messageRepository.save(message);

    }

    @Override
    @Transactional
    public List<Message> getAll() {
        log.info("Получение списка всех файлов");
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    public String deleteById(Long id) {
        String name = messageRepository.getById(id).getOriginFileName();
        messageRepository.deleteById(id);
        return name;
    }


}
