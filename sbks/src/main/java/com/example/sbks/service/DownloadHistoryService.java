package com.example.sbks.service;

import com.example.sbks.model.DownloadHistory;

import java.util.List;

/**
 * Сервис для работы с историей скачивания файлов
 */
public interface DownloadHistoryService {

    /**
     * Запись события о скачивании файла
     */
    DownloadHistory save(DownloadHistory downloadHistory);

    /**
     * Получение всей истории скачивания файла по его id
     */
    List<DownloadHistory> getAllById(Long id);
}
