package com.example.sbks.repository;

import com.example.sbks.model.DownloadHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс для взаимодействия с БД для хранения истории скачивания файлов
 */
@Repository
public interface DownloadHistoryRepository extends PagingAndSortingRepository<DownloadHistory, Long> {

    /**
     * Получение истории скачивания файла по его id
     */
    List<DownloadHistory> findAllByMessageId(Long id, Pageable pageable);

}
