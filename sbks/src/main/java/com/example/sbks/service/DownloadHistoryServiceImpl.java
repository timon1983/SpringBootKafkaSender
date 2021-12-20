package com.example.sbks.service;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.exception.NoSuchDataFileException;
import com.example.sbks.model.DownloadHistory;
import com.example.sbks.repository.DownloadHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadHistoryServiceImpl implements DownloadHistoryService {

    private final static Logger log = LogManager.getLogger(DownloadHistoryServiceImpl.class);
    private final DownloadHistoryRepository downloadHistoryRepository;
    private final MessageService messageService;

    @Transactional
    @Override
    public DownloadHistoryDto saveByName(DownloadHistory downloadHistory) {
        messageService.getByName(downloadHistory.getFileName())
                .map(message -> {
                    downloadHistory.setMessage(message);
                    log.info("Запись события скачивания файла");
                    downloadHistoryRepository.save(downloadHistory);
                    return message;
                }).orElseThrow(() -> new NoSuchDataFileException(
                        String.format("Данные о файле с именем %s в БД отсутствуют",
                                downloadHistory.getFileName()))
                );
        return getDtoInfoModel(downloadHistory);
    }

    @Transactional
    @Override
    public DownloadHistoryDto saveById(DownloadHistory downloadHistory) {
        messageService.getById(downloadHistory.getId())
                .map(message -> {
                    downloadHistory.setMessage(message);
                    downloadHistory.setFileName(message.getOriginFileName());
                    log.info("Запись события скачивания файла");
                    downloadHistoryRepository.save(downloadHistory);
                    return message;
                }).orElseThrow(() -> new NoSuchDataFileException(
                        String.format("Данные о файле с id=%d в БД отсутствуют",
                                downloadHistory.getId()))
                );
        return getDtoInfoModel(downloadHistory);
    }

    @Override
    @Transactional
    public List<DownloadHistory> getAllById(Long id) {
        log.info("Получение всю историю скачиваний файла с id={}", id);
        return downloadHistoryRepository.findAllByMessageId(id);
    }

    private DownloadHistoryDto getDtoInfoModel(DownloadHistory downloadHistory) {
        return DownloadHistoryDto.builder()
                .info(downloadHistory.getMessage().getFileNameForS3())
                .build();
    }
}
