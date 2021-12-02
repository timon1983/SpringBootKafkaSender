package com.example.SpringBootKafkaSender.service;

import com.example.SpringBootKafkaSender.model.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface MessageService {
    void save(Message message, MultipartFile multipartFile);
    List<Message> getAll();
}
