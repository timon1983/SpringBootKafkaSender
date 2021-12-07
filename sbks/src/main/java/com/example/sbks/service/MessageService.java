package com.example.sbks.service;

import com.example.sbks.model.Message;

import java.util.List;
import java.util.Optional;


public interface MessageService {
    Message save(Message message);

    List<Message> getAll();

    Message deleteById(Long id);

    Optional<Message> getById(Long id);
}
