package com.example.sbks.service;

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

    @Override
    @Transactional
    public DownloadHistory save(DownloadHistory downloadHistory) {
        log.info("Запись события скачивания файла");
        return downloadHistoryRepository.save(downloadHistory);
    }


    @Override
    @Transactional
    public List<DownloadHistory> getAllById(Long id) {
        log.info("Получили всю историю скачиваний файла с id={}", id);
        return downloadHistoryRepository.findAllByMessageId(id);
    }
}
