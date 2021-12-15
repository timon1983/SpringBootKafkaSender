package com.example.sbks.controller;

import com.example.sbks.model.Message;
import com.example.sbks.service.MessageDeletedService;
import com.example.sbks.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для обработки запросов по операциям с удаленными файлами
 */
@RestController
@RequestMapping("/api/sdk")
@RequiredArgsConstructor
public class MessageDeletedController {

    private final static Logger log = LogManager.getLogger(MessageDeletedController.class);
    private final MessageDeletedService messageDeletedService;
    private final MessageService messageService;

    /**
     * Получение списка удаленных файлов
     */
    @GetMapping("/files-deleted")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageDeletedService.getAll();
        log.info("Контроллер.Запрос в сервис на получение списка удаленных файлов");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/files-clean")
    public ResponseEntity<List<Message>> deleteAllMessages() {
        messageDeletedService.deleteAll();
        List<Message> messages = messageDeletedService.getAll();
        log.info("Контроллер.Запрос на очистку списка удаленных файлов");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * Удаление файла на совсем
     */
    @PostMapping("/full-delete")
    public ResponseEntity<String> deletingTheFileAtAll(@RequestBody Long id) {
        log.info("Контроллер.Запрос на полное удаление файла по id={}", id);
        messageDeletedService.fullDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Восстановление файла из корзины по его id
     */
    @PostMapping("/restore-file")
    public ResponseEntity<String> restoreMessageById(@RequestBody Long id) {
        log.info("Контроллер.Запрос на восстановление файла по id={}", id);
        messageDeletedService.restoreMessage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
