package com.example.sbks.service;

import com.example.sbks.dto.MessageDto;
import com.example.sbks.model.Message;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с БД messages
 */
public interface MessageService {

    /**
     * Запись сообщения в БД
     */
    MessageDto save(MessageDto messageDto);

    /**
     * Получение списка всех сохраненных записей о файлах
     */
    List<MessageDto> getAll();

    /**
     * Удаление данных файла из БД по его ID
     */
    void deleteById(Long id);

    /**
     * Получение информации о файле по его id
     */
    Optional<Message> getById(Long id);

    /**
     * Получение информации о файле по имени
     */
    Optional<Message> getByName(String name);

    void throwException();
}
