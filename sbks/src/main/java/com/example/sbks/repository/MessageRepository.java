package com.example.sbks.repository;

import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для взаимодействия с БД messages
 */
@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    /**
     * Получение сущности по оригинальному имени файла
     */
    List<Optional<Message>>findByOriginFileName(String name);

    /**
     * Удаление всех записей где присутствует дата и время удаления файла
     */
    void deleteAllByStatus(Status status);

    /**
     * Получение списка сущностей где отсутствует дата и время удаления файла
     */
    List<Message> findAllByStatus(Status status, Pageable pageable);

    @Query("select m.fileNameForS3 from Message m where m.status = ?1")
    List<String> findAllFileNameForS3ByStatus(Status status, Pageable pageable);
}
