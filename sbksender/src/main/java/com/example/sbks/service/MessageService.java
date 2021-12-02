package com.example.sbks.service;

import com.example.sbks.model.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface MessageService {
    void save(Message message, MultipartFile multipartFile);
    List<Message> getAll();
}
