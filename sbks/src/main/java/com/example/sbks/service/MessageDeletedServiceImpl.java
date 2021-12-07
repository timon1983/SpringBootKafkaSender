package com.example.sbks.service;

import com.example.sbks.model.MessageDeleted;
import com.example.sbks.repository.MessageDeletedRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageDeletedServiceImpl implements MessageDeletedService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageDeletedRepository messageDeletedRepository;

    /**
     * Запись данных удаленного файла в БД
     */
    @Override
    @Transactional
    public MessageDeleted save(MessageDeleted messageDeleted) {

        log.info("Запись удаленного сообщения в бд");
        return messageDeletedRepository.save(messageDeleted);
    }

    /**
     * Получение списка всех удаленных файлов
     */
    @Override
    @Transactional
    public List<MessageDeleted> getAll() {

        log.info("Получение списка всех удаленных файлов");
        return messageDeletedRepository.findAll();
    }
}
