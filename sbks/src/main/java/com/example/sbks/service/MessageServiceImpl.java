package com.example.sbks.service;

import com.example.sbks.model.Message;
import com.example.sbks.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public Message save(Message message) {
        log.info("Service.Запись сообщения в бд");
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public List<Message> getAll() {
        log.info("Service.Получение списка всех файлов");
        return messageRepository.findAllSavedMessages();
    }

    @Override
    @Transactional
    public Optional<Message> deleteById(Long id) {
        var now = LocalDateTime.now();
        log.info("Service.Удаление данных файла из БД по его ID={}", id);
        messageRepository.writeDataDelete(now.toLocalDate(), now.toLocalTime().withNano(0), id);
        return messageRepository.findById(id);
    }

    @Override
    public Optional<Message> getById(Long id) {
        log.info("Service.Получение информации о файле по его id={}", id);
        return messageRepository.findById(id);
    }

    @Override
    public Optional<Message> getByName(String name) {
        log.info("Service.Получение информации о файле по его name={}", name);
        return messageRepository.findByOriginFileName(name);
    }
}
