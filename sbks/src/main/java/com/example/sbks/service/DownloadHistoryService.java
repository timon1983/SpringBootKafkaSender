package com.example.sbks.service;

import com.example.sbks.dto.DownloadClientInfo;
import com.example.sbks.model.DownloadHistory;
import com.example.sbks.model.Message;

import java.util.List;

/**
 * Сервис для работы с историей скачивания файлов
 */
public interface DownloadHistoryService {

    /**
     * Запись события о скачивании файла
     */
    void save(DownloadClientInfo downloadClientInfo, Message message);

    /**
     * Получение всей истории скачивания файла по его id
     */
    List<DownloadHistory> getAllById(Long id);
}
