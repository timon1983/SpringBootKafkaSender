package com.example.sbks.service;

import com.example.sbks.dto.DTOInfoModel;
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

    @Override
    @Transactional
    public DTOInfoModel saveById(DownloadHistory downloadHistory) {
        DTOInfoModel dtoInfoModel = new DTOInfoModel();
        messageService.getById(downloadHistory.getId())
                .ifPresentOrElse(message -> {
                    downloadHistory.setFileName(message.getOriginFileName());
                    downloadHistory.setMessage(message);
                    dtoInfoModel.setInfo(message.getFileNameForS3());
                    dtoInfoModel.setIsError(false);
                }, () -> {
                    throw new NoSuchDataFileException(String.format("Данные о файле с id=%d в БД отсутствуют",
                            downloadHistory.getId()));
                });
        log.info("Запись события скачивания файла");
        downloadHistoryRepository.save(downloadHistory);
        return dtoInfoModel;
    }

    @Override
    public DTOInfoModel saveByName(DownloadHistory downloadHistory) {
        DTOInfoModel dtoInfoModel = new DTOInfoModel();
        messageService.getByName(downloadHistory.getFileName())
                .ifPresentOrElse(message -> {
                    downloadHistory.setMessage(message);
                    dtoInfoModel.setInfo(message.getFileNameForS3());
                    dtoInfoModel.setIsError(false);
                }, () -> {
                    throw new NoSuchDataFileException(String.format("Данные о файле с именем %s в БД отсутствуют",
                            downloadHistory.getFileName()));
                });
        log.info("Запись события скачивания файла");
        downloadHistoryRepository.save(downloadHistory);
        return dtoInfoModel;
    }

    @Override
    @Transactional
    public List<DownloadHistory> getAllById(Long id) {
        log.info("Получение всю историю скачиваний файла с id={}", id);
        return downloadHistoryRepository.findAllByMessageId(id);
    }


}
