package com.example.sbks.service;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.exception.NoSuchDataFileException;
import com.example.sbks.mapper.MapperForModel;
import com.example.sbks.model.DownloadHistory;
import com.example.sbks.repository.DownloadHistoryRepository;
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
public class DownloadHistoryServiceImpl implements DownloadHistoryService {

    private final static Logger log = LogManager.getLogger(DownloadHistoryServiceImpl.class);
    private final MapperForModel mapper = Mappers.getMapper(MapperForModel.class);
    private final DownloadHistoryRepository downloadHistoryRepository;
    private final MessageRepository messageRepository;

    @Transactional
    @Override
    public DownloadHistoryDto saveByName(DownloadHistoryDto downloadHistoryDto) {
        DownloadHistory downloadHistory = mapper.dtoToDownloadHistory(downloadHistoryDto);
        messageRepository.findByOriginFileName(downloadHistory.getFileName())
                .map(message -> {
                    downloadHistory.setMessage(message);
                    downloadHistoryDto.setFileName(message.getFileNameForS3());
                    log.info("Запись события скачивания файла");
                    downloadHistoryRepository.save(downloadHistory);
                    return message;
                })
                .orElseThrow(
                        () -> new NoSuchDataFileException(
                                String.format("Данные о файле с именем %s в БД отсутствуют",
                                        downloadHistory.getFileName())
                        )
                );
        return downloadHistoryDto;
    }

    @Transactional
    @Override
    public DownloadHistoryDto saveById(DownloadHistoryDto downloadHistoryDto) {
        DownloadHistory downloadHistory = mapper.dtoToDownloadHistory(downloadHistoryDto);
        messageRepository.findById(downloadHistoryDto.getId())
                .map(message -> {
                    downloadHistory.setMessage(message);
                    downloadHistory.setFileName(message.getOriginFileName());
                    downloadHistoryDto.setFileName(message.getFileNameForS3());
                    log.info("Запись события скачивания файла");
                    downloadHistoryRepository.save(downloadHistory);
                    return message;
                }).orElseThrow(() -> new NoSuchDataFileException(
                String.format("Данные о файле с id=%d в БД отсутствуют",
                        downloadHistory.getId()))
        );
        return downloadHistoryDto;
    }

    @Override
    @Transactional // todo лищний
    public List<DownloadHistoryDto> getAllById(Long id) {
        log.info("Получение всю историю скачиваний файла с id={}", id);
        return downloadHistoryRepository.findAllByMessageId(id)
                .stream()
                .map(mapper::downloadHistoryToDto)
                .collect(Collectors.toList());
    }
}
