package com.example.SpringBootKafkaSender.controller;

import com.example.SpringBootKafkaSender.model.Message;
import com.example.SpringBootKafkaSender.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/create")
public class MessageController {

    private final static Logger log = LogManager.getLogger(MessageController.class);
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("")
    public String getCreatePage() {
        return "message-insert-form";
    }

    /**
     * Получение сообщения от клиента
     */
    @PostMapping("")
    public String createMassage(MultipartHttpServletRequest request) throws ServletException, IOException {
        log.info("Receive message");
        MultipartFile multipartFile = request.getFile("file");
        Message message = Message.builder()
                .title(request.getParameter("title"))
                .size(request.getPart("file").getSize())
                .dateOfCreate(LocalDate.now())
                .author(request.getParameter("author"))
                .content(request.getPart("file").getSubmittedFileName())
                .contentType(request.getPart("file").getContentType())
                .build();
        messageService.save(message, multipartFile);
        return "message-insert-form";
    }
}
