package com.example.sbks.service;

import com.example.sbks.dto.DownloadHistoryDto;

import java.util.List;

/**
 * Сервис для работы с историей скачивания файлов
 */
public interface DownloadHistoryService {

    /**
     * Запись события о скачивании файла по имени
     */
    DownloadHistoryDto saveByName(DownloadHistoryDto downloadHistoryDto);

    /**
     * Получение всей истории скачивания файла по его id
     */
    List<DownloadHistoryDto> getAllById(Long id);

    /**
     * Запись события о скачивании файла по id
     */
    DownloadHistoryDto saveById(DownloadHistoryDto downloadHistoryDto);
}
