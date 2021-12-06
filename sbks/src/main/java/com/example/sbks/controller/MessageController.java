package com.example.sbks.controller;

import com.example.sbks.model.Message;
import com.example.sbks.service.MessageService;
import com.example.uisbks.dtomodel.DTOMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/sdk")
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
    @PostMapping("/create")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        //System.out.println(modelMessageDTO);
        log.info("Получение сообщения от клиента");
//        Message message = Message.builder()
//                .title(modelMessageDTO.getTitle())
//                .size(modelMessageDTO.getSize())
//                .dateOfCreate(modelMessageDTO.getDateOfCreate())
//                .timeOfCreate(modelMessageDTO.getTimeOfCreate())
//                .author(modelMessageDTO.getAuthor())
//                .originFileName(modelMessageDTO.getOriginFileName())
//                .fileNameForS3(modelMessageDTO.getFileNameForS3())
//                .contentType(modelMessageDTO.getContentType())
//                .build();

//        Message message = Message.builder()
//                .title((String) model.getAttribute("title"))
//                .size((Long) model.getAttribute("size"))
//                .dateOfCreate((LocalDate) model.getAttribute("dateOfCreate"))
//                .timeOfCreate((LocalTime) model.getAttribute("timeOfCreate"))
//                .author((String) model.getAttribute("author"))
//                .originFileName((String) model.getAttribute("originFileName"))
//                .fileNameForS3((String) model.getAttribute("fileNameForS3"))
//                .contentType((String) model.getAttribute("contentType"))
//                .build();
        Message savedMessage = messageService.save(message);
        //modelMessageDTO.setId(savedMessage.getId());
        return new ResponseEntity<>( savedMessage, HttpStatus.OK);
    }

}
