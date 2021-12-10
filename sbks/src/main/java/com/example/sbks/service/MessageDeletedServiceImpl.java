package com.example.sbks.service;

import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import com.example.sbks.repository.MessageRepository;
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
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public List<Message> getAll() {
        log.info("Service.Получение списка всех удаленных файлов");
        return messageRepository.findAllByStatus(Status.DELETED);
    }

    @Override
    @Transactional
    public List<Message> deleteAll() {
        messageRepository.deleteAllByStatus(Status.DELETED);
        log.info("Service.Записи в таблице удалены");
        return getAll();
    }
}
