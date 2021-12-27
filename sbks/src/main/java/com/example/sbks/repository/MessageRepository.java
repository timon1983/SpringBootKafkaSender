package com.example.sbks.repository;

import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для взаимодействия с БД
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Получение сущности по оригинальному имени файла
     */
    Optional<Message> findByOriginFileName(String name);

    /**
     * Удаление всех записей где присутствует дата и время удаления файла
     */
    void deleteAllByStatus(Status status);

    /**
     * Получение списка сущностей где отсутствует дата и время удаления файла
     */
    List<Message> findAllByStatus(Status status);

    @Query("select m.fileNameForS3 from Message m where m.status = ?1")
    List<String> findAllFileNameForS3ByStatus(Status status);
}
