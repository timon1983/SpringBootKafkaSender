package com.example.sbks.controller;

import com.example.sbks.dto.DownloadClientInfo;
import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import com.example.sbks.service.DownloadHistoryService;
import com.example.sbks.service.MessageSenderService;
import com.example.sbks.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Контроллер для обработки запросов по основным операциям с файлами
 */
@RestController
@RequestMapping("/api/sdk")
@RequiredArgsConstructor
public class MessageController {

    private final static Logger log = LogManager.getLogger(MessageController.class);
    private final MessageService messageService;
    private final MessageSenderService messageSenderService;
    private final DownloadHistoryService downloadHistoryService;

    /**
     * Переход на страницу добавления файла
     */
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
        message.setStatus(Status.UPLOAD);
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
     * Скачивание файла по id
     */
    @PostMapping("/open-id")
    public ResponseEntity<Message> findById(@RequestBody DownloadClientInfo downloadClientInfo) {
        Message message = messageService.getById(downloadClientInfo).orElse(new Message());
        downloadHistoryService.save(downloadClientInfo, message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получение файла по имени
     */
    @PostMapping("/open-name")
    public ResponseEntity<Message> findByName(@RequestBody DownloadClientInfo downloadClientInfo) throws UnsupportedEncodingException {
        String name = downloadClientInfo.getFileName();
        name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        Message message = messageService.getByName(name).orElse(new Message());
        downloadHistoryService.save(downloadClientInfo, message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Оправка файла в MessageSenderSender
     */
    @PostMapping("/send-file")
    public ResponseEntity<Message> receiveMessageForSendToMessageSender(@RequestBody String name) throws UnsupportedEncodingException, URISyntaxException {
        name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        Message message = messageService.getByName(name).orElse(new Message());
        messageSenderService.sendMessage(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
