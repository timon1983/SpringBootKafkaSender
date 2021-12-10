package com.example.sbks.service;

import com.example.sbks.model.Message;

import java.net.URISyntaxException;

/**
 * Сервис для отправки сообщения в службу отправки сообщений
 */
public interface MessageSenderService {

    /**
     * Метод для отправки сообщения
     */
    void sendMessage(Message message) throws URISyntaxException;
}
