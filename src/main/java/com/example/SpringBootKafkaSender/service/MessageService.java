package com.example.SpringBootKafkaSender.service;

import com.example.SpringBootKafkaSender.model.Message;
import org.springframework.web.multipart.MultipartFile;


public interface MessageService {
    void save(Message message, MultipartFile multipartFile);
}
