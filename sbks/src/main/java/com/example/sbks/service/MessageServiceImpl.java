package com.example.sbks.service;

import com.amazonaws.services.cloudfront.model.Paths;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.exception.NoSuchDataFileException;
import com.example.sbks.mapper.MapperForModel;
import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import com.example.sbks.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final MapperForModel mapper = Mappers.getMapper(MapperForModel.class);
    private final MessageRepository messageRepository;
    private final ServiceS3 serviceS3;

    @Override
    @Transactional
    public MessageDto save(MessageDto messageDto) {
        File file = Path.of(messageDto.getFileNameForS3()).toFile();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(messageDto.getContent());
        } catch (IOException e) {
            throw new NoSuchDataFileException(String.format("Ошибка при считывании файла %s",
                    messageDto.getOriginFileName()));
        }
        serviceS3.upload(file);
        Message message = mapper.dtoToMessage(messageDto);
        message.setStatus(Status.UPLOAD);
        log.info("Service.Запись сообщения в бд");
        messageRepository.save(message);
        return messageDto;
    }

    @Override
    @Transactional
    public List<MessageDto> getAll() {
        log.info("Service.Получение списка всех файлов");
        return messageRepository
                .findAllByStatus(Status.UPLOAD)
                .stream()
                .map(mapper::messageToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        messageRepository.findById(id).ifPresentOrElse(message -> {
            message.setStatus(Status.DELETED);
            message.setDateOfDelete(LocalDateTime.now().withNano(0));
            log.info("Service.Удаление данных файла из БД по его ID={}", id);
            messageRepository.save(message);
        }, () -> {
            throw new NoSuchDataFileException(String.format("Данные о файле с id=%d в БД отсутствуют", id));
        });
    }

    @Override
    @Transactional
    public Optional<Message> getById(Long id) {
        log.info("Service.Получение информации о файле по его id={}", id);
        return messageRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Message> getByName(String name) {
        log.info("Service.Получение информации о файле по его name={}", name);
        return messageRepository.findByOriginFileName(name);
    }
}
