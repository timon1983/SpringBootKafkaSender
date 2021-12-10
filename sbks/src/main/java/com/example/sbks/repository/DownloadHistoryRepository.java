package com.example.sbks.repository;

import com.example.sbks.model.DownloadHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс для взаимодействия с БД для хранения истории скачивания файлов
 */
@Repository
public interface DownloadHistoryRepository extends JpaRepository<DownloadHistory, Long> {

    /**
     * Получение истории скачивания файла по его id
     */
    List<DownloadHistory> findAllByMessageId(Long id);

}
