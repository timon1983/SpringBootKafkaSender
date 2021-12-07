package com.example.sbks.controller;

import com.example.sbks.model.Message;
import com.example.sbks.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/sdk")
@RequiredArgsConstructor
public class MessageController {

    private final static Logger log = LogManager.getLogger(MessageController.class);
    private final MessageService messageService;

    @GetMapping
    public String getCreatePage() {
        return "message-insert-form";
    }

    /**
     * Получение сообщения от клиента
     */
    @PostMapping("/create")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        System.out.println(message);
        log.info("Получение сообщения от клиента и запись в БД");
        Message savedMessage = messageService.save(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.OK);
    }

    /**
     * Получение списка всех загруженных файлов
     */
    @GetMapping("/files")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * Удаление файла по id
     */
    @PostMapping("/delete")
    public ResponseEntity<Message> deleteById(@RequestBody Long id) {
        Message deletedMessage = messageService.deleteById(id);
        return new ResponseEntity<>(deletedMessage, HttpStatus.OK);
    }

    /**
     * Получение файла по id
     */
    @PostMapping("/open-id")
    public ResponseEntity<Message> findById(@RequestBody Long id) {
        Message message = messageService.getById(id).orElse(new Message());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получение файла по имени
     */
    @PostMapping("/open-name")
    public ResponseEntity<Message> findByName(@RequestBody String name) throws UnsupportedEncodingException {
        name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        Message message = messageService.getByName(name).orElse(new Message());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
