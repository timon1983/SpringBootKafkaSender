package com.example.sbks.service;

import com.example.sbks.dto.MessageDto;
import com.example.sbks.exception.NoSuchDataFileException;
import com.example.sbks.mapper.MapperForModel;
import com.example.sbks.model.Status;
import com.example.sbks.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageDeletedServiceImpl implements MessageDeletedService {

    private final static Logger log = LogManager.getLogger(MessageDeletedServiceImpl.class);
    private final MapperForModel mapper = Mappers.getMapper(MapperForModel.class);
    private final MessageRepository messageRepository;
    private final ServiceS3 serviceS3;

    @Override
    @Transactional
    public List<MessageDto> getAll() {
        log.info("Service.Получение списка всех удаленных файлов");
        // todo через Pageable
        return messageRepository.findAllByStatus(Status.DELETED)
                .stream()
                .map(mapper::messageToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<String> deleteAll() {
        List<String> fileNames = messageRepository.findAllFileNameForS3ByStatus(Status.DELETED);
        fileNames.forEach(serviceS3::delete);
        messageRepository.deleteAllByStatus(Status.DELETED);
        log.info("Service.Записи в таблице удалены");
        return fileNames;
    }

    @Override
    @Transactional
    public String fullDelete(Long id) {
        return messageRepository
                .findById(id)
                .map(message -> {
                    serviceS3.delete(message.getFileNameForS3());
                    messageRepository.deleteById(id);
                    log.info("Service.Файл с id={} полностью удален", id);
                    return message.getFileNameForS3();
                })
                .orElseThrow(
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
                // todo ifPresentOrElse заменить на отдельный map и orElseThrow
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
