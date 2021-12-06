package com.example.sbks.service;

import com.example.sbks.model.Message;

import java.util.List;


public interface MessageService {
    Message save(Message message);

    List<Message> getAll();

    String deleteById(Long id);
}
