package com.example.sbks.service;

import com.example.sbks.dto.MessageDto;
import com.example.sbks.model.Message;

import java.util.List;

/**
 * Сервис для работы с информацией по удаленным сообщениям
 */
public interface MessageDeletedService {

    /**
     * Получение списка всех удаленных файлов
     */
    List<MessageDto> getAll();

    /**
     * Удаление всех записей в таблице messages_deleted
     */
    List<String> deleteAll();

    /**
     * Удаление всех записи на совсем в таблице по id
     */
    String fullDelete(Long id);

    /**
     * Восстановление записи(установка значения status = UPLOAD)
     */
    void restoreMessage(Long id);
}
