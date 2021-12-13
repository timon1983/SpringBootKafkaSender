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

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageDeletedServiceImpl implements MessageDeletedService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;
    private final ServiceS3 serviceS3;

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

    @Override
    @Transactional
    public Message fullDelete(Long id) {
        Message message = messageRepository.findById(id).orElse(null);
        if (message != null) {
            messageRepository.deleteById(id);
            log.info("Service.Запись в таблице с id=" + id + " удалена");
        } else {
            log.info("Service.Записи в таблице с id=" + id + " нет");
        }
        log.info("Service.Записи в таблице с id=" + id + " нет");
        return message;
    }

    public void fullDelete2(Long id) {
        messageRepository.findById(id).ifPresent(message -> {
                    log.info("Service.Запись в таблице с id=" + id + " удалена");
                    messageRepository.deleteById(id);
                }
        );
    }

    @Override
    @Transactional
    public Message changeMessage(Long id) {
        Message message = messageRepository.findById(id).orElse(null);
        if (message != null) {
            message.setStatus(Status.UPLOAD);
            message.setDateOfDelete(null);
            log.info("Service.Восстановление данных файла из БД по его ID={}", id);
            return messageRepository.save(message);
        }
        log.info("Service.Записи в таблице с id=" + id + " нет");
        return message;
    }
}
