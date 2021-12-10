package com.example.sbks.controller;

import com.example.sbks.model.Message;
import com.example.sbks.service.MessageDeletedService;
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

    private final static Logger log = LogManager.getLogger(MessageController.class);
    private final MessageDeletedService messageDeletedService;

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
        List<Message> messages = messageDeletedService.deleteAll();
        log.info("Контроллер.Запрос на очистку списка удаленных файлов");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * Удаление файла на совсем
     */
    @PostMapping("/full-delete")
    public ResponseEntity<Message> deletingTheFileAtAll(@RequestBody Long id) {
        Message message = messageDeletedService.fullDelete(id);
        log.info("Контроллер.Запрос на удаление файла на совсем по id={}", id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Восстановление файла из корзины по его id
     */
    @PostMapping("/restore-file")
    public ResponseEntity<Message> restoreMessageById(@RequestBody Long id) {
        Message message = messageDeletedService.changeMessage(id);
        log.info("Контроллер.Запрос на восстановление файла по id={}", id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
