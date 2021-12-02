package com.example.sbks.controller;

import com.example.sbks.model.Message;
import com.example.sbks.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/create")
public class MessageController {

    private final static Logger log = LogManager.getLogger(MessageController.class);
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String getCreatePage() {
        return "message-insert-form";
    }

    /**
     * Получение сообщения от клиента
     */
    @PostMapping
    public String createMessage(MultipartHttpServletRequest request) throws ServletException, IOException {
        log.info("Получение сообщения от клиента");
        MultipartFile multipartFile = request.getFile("file");
        Message message = Message.builder()
                .title(request.getParameter("title"))
                .size(request.getPart("file").getSize())
                .dateOfCreate(LocalDateTime.now().withNano(0))
                .author(request.getParameter("author"))
                .content(request.getPart("file").getSubmittedFileName())
                .contentType(request.getPart("file").getContentType())
                .build();
        messageService.save(message, multipartFile);
        return "message-insert-form";
    }

}
