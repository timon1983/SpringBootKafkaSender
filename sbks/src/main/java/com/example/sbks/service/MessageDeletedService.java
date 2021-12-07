package com.example.sbks.service;

import com.example.sbks.model.MessageDeleted;

import java.util.List;

public interface MessageDeletedService {

    MessageDeleted save(MessageDeleted messageDeleted);

    List<MessageDeleted> getAll();

    List<MessageDeleted> deleteAll();
}
