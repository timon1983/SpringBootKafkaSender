package com.example.sbks.service;

import com.example.sbks.model.Message;

import java.util.List;

/**
 * Сервис для работы с информацией по удаленным сообщениям
 */
public interface MessageDeletedService {

    /**
     * Получение списка всех удаленных файлов
     */
    List<Message> getAll();

    /**
     * Удаление всех записей в таблице messages_deleted
     */
    List<Message> deleteAll();
}
