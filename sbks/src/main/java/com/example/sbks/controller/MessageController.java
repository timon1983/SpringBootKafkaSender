package com.example.sbks.controller;

import com.example.sbks.model.Message;
import com.example.sbks.service.MessageService;
import com.example.uisbks.dtomodel.DTOMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DTOMessage> createMessage(@RequestBody DTOMessage modelMessageDTO) {
        System.out.println(modelMessageDTO);
        log.info("Получение сообщения от клиента");
        Message message = Message.builder()
                .title(modelMessageDTO.getTitle())
                .size(modelMessageDTO.getSize())
                .dateOfCreate(modelMessageDTO.getDateOfCreate())
                .timeOfCreate(modelMessageDTO.getTimeOfCreate())
                .author(modelMessageDTO.getAuthor())
                .originFileName(modelMessageDTO.getOriginFileName())
                .fileNameForS3(modelMessageDTO.getFileNameForS3())
                .contentType(modelMessageDTO.getContentType())
                .build();
        Message savedMessage = messageService.save(message);
        modelMessageDTO.setId(savedMessage.getId());
        return new ResponseEntity<>(modelMessageDTO, HttpStatus.OK);
    }

}
