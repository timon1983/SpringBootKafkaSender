package com.example.sbks.repository;

import com.example.sbks.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
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
     * Запись даты и времени удаления файла
     */
    @Modifying
    @Query(value = "UPDATE messages SET date_delete = :dateOfDelete, time_delete = :timeOfDelete WHERE id = :id",
            nativeQuery = true)
    void writeDataDelete(@Param("dateOfDelete") LocalDate dateOfDelete, @Param("timeOfDelete") LocalTime timeOfDelete,
                         @Param("id") Long id);

    /**
     * Получение списка сущностей где присутствует дата и время удаления файла
     */
    @Query(value = "SELECT * FROM messages WHERE date_delete is not null AND  time_delete is not null", nativeQuery = true)
    List<Message> findAllDeletedMessages();

    /**
     * Удаление всех записей где присутствует дата и время удаления файла
     */
    @Modifying
    @Query(value = "DELETE FROM messages WHERE date_delete is not null AND time_delete is not null", nativeQuery = true)
    void deleteAllDeletedMessages();

    /**
     * Получение списка сущностей где отсутствует дата и время удаления файла
     */
    @Query(value = "SELECT * FROM messages WHERE date_delete is null AND  time_delete is null", nativeQuery = true)
    List<Message> findAllSavedMessages();
}
