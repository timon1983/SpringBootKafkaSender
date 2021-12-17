package com.example.sbks.service;

import com.example.awsS3.service.ServiceS3;
import com.example.sbks.exception.NoSuchDataFileException;
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

    private final static Logger log = LogManager.getLogger(MessageDeletedServiceImpl.class);
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
    public void deleteAll() {
        List<Message> messages = getAll();
        messages.forEach(message -> serviceS3.delete(message.getFileNameForS3()));
        messageRepository.deleteAllByStatus(Status.DELETED);
        log.info("Service.Записи в таблице удалены");
    }

    @Override
    @Transactional
    public void fullDelete(Long id) {
        messageRepository
                .findById(id)
                .ifPresentOrElse(message -> {
                            serviceS3.delete(message.getFileNameForS3());
                            messageRepository.deleteById(id);
                            log.info("Service.Файл с id={} полностью удален", id);
                        },
                        () -> {
                            throw new NoSuchDataFileException(String.format("Данные о файле с id=%d в БД отсутствуют",
                                    id));
                        });
    }

    @Override
    @Transactional
    public void restoreMessage(Long id) {
        messageRepository
                .findById(id)
                .ifPresentOrElse(message -> {
                            message.setStatus(Status.UPLOAD);
                            message.setDateOfDelete(null);
                            messageRepository.save(message);
                        },
                        () -> {
                            throw new NoSuchDataFileException(String.format("Данные о файле с id=%d в БД отсутствуют",
                                    id));
                        });
    }
}
