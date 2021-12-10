package com.example.sbks.service;

import com.example.sbks.dto.DownloadClientInfo;
import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
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
        return messageRepository.findAllByStatus(Status.UPLOAD);
    }

    @Override
    @Transactional
    public Message deleteById(Long id) {
        Message message = messageRepository.findById(id).orElse(new Message());
        message.setStatus(Status.DELETED);
        message.setDateOfDelete(LocalDateTime.now().withNano(0));
        log.info("Service.Удаление данных файла из БД по его ID={}", id);
        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> getById(DownloadClientInfo downloadClientInfo) {
        log.info("Service.Получение информации о файле по его id={}", downloadClientInfo.getIdFile());
        return messageRepository.findById(downloadClientInfo.getIdFile());
    }

    @Override
    public Optional<Message> getByName(String name) {
        log.info("Service.Получение информации о файле по его name={}", name);
        return messageRepository.findByOriginFileName(name);
    }
}
