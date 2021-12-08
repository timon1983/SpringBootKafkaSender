package com.example.sbks.service;

import com.example.sbks.model.Message;

import java.net.URISyntaxException;

public interface MessageSenderService {

    void sendMessage(Message message) throws URISyntaxException;
}
