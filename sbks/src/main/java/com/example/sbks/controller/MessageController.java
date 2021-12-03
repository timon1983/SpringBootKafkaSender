package com.example.sbks.controller;

import com.example.sbks.model.Message;
import com.example.sbks.service.MessageService;

import com.example.uisbks.dto.MessageDTO;
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
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        System.out.println(messageDTO);
        log.info("Получение сообщения от клиента");
        Message message = Message.builder()
                .title(messageDTO.getTitle())
                .size(messageDTO.getSize())
                .dateOfCreate(messageDTO.getDateOfCreate())
                .timeOfCreate(messageDTO.getTimeOfCreate())
                .author(messageDTO.getAuthor())
                .originFileName(messageDTO.getOriginFileName())
                .fileNameForS3(messageDTO.getFileNameForS3())
                .contentType(messageDTO.getContentType())
                .build();
        Message savedMessage = messageService.save(message);
        messageDTO.setId(savedMessage.getId());
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

}
