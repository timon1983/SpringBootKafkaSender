package com.example.sbks.service;

import com.example.sbks.model.Message;
import com.example.sbks.model.MessageDeleted;
import com.example.sbks.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;
    private final MessageDeletedService messageDeletedService;


    /**
     * Запись сообщения в БД
     */
    @Override
    @Transactional
    public Message save(Message message) {

        log.info("Запись сообщения в бд");
        return messageRepository.save(message);
    }

    /**
     * Получение списка всех сохраненных файлов
     */
    @Override
    @Transactional
    public List<Message> getAll() {
        log.info("Получение списка всех файлов");
        return messageRepository.findAll();
    }

    /**
     * Удаление данных файла из БД по его ID
     */
    @Override
    @Transactional()
    public Message deleteById(Long id) {
        Message message = getById(id).orElse(null);
        if (message != null) {
            MessageDeleted messageDeleted = createMessageDeleted(message);
            log.info("Удаление данных файла {} из БД по его ID", message.getOriginFileName());
            messageRepository.deleteById(id);
            log.info("Сохранение данных удаленного файла {} в БД удаленных файлов", message.getOriginFileName());
            messageDeletedService.save(messageDeleted);
        } else {
            log.error("Нет файла в БД с id={}", id);
            return new Message();
        }
        return message;
    }

    @Override
    public Optional<Message> getById(Long id) {
        return messageRepository.findById(id);
    }

    /**
     * Получение объекта MessageDeleted
     */
    public MessageDeleted createMessageDeleted(Message message) {
        return MessageDeleted.builder()
                .title(message.getTitle())
                .size(message.getSize())
                .dateOfDelete(LocalDate.now())
                .timeOfDelete(LocalTime.now().withNano(0))
                .author(message.getAuthor())
                .originFileName(message.getOriginFileName())
                .contentType(message.getContentType())
                .build();
    }
}
