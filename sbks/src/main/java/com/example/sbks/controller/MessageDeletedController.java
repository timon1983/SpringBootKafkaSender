package com.example.sbks.controller;

import com.example.sbks.model.MessageDeleted;
import com.example.sbks.service.MessageDeletedService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sdk")
@RequiredArgsConstructor
public class MessageDeletedController {

    private final static Logger log = LogManager.getLogger(MessageController.class);
    private final MessageDeletedService messageDeletedService;

    /**
     * Получение списка удаленных файлов
     */
    @GetMapping("/files-deleted")
    public ResponseEntity<List<MessageDeleted>> getAllMessages() {
        List<MessageDeleted> messages = messageDeletedService.getAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/files-clean")
    public ResponseEntity<List<MessageDeleted>> deleteAllMessages() {
        List<MessageDeleted> messages = messageDeletedService.deleteAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
