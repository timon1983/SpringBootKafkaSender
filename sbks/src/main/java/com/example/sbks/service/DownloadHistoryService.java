package com.example.sbks.service;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.dto.InfoDto;
import com.example.sbks.model.DownloadHistory;

import java.util.List;

/**
 * Сервис для работы с историей скачивания файлов
 */
public interface DownloadHistoryService {

    /**
     * Запись события о скачивании файла
     */
    DownloadHistoryDto saveByName(DownloadHistoryDto downloadHistoryDto);

    /**
     * Получение всей истории скачивания файла по его id
     */
    List<DownloadHistoryDto> getAllById(Long id);

    DownloadHistoryDto saveById(DownloadHistoryDto downloadHistoryDto);
}
