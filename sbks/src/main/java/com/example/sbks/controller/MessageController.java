package com.example.sbks.controller;

import com.example.sbks.model.DownloadHistory;
import com.example.sbks.model.Message;
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
import java.util.Optional;

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
        log.info("Получение сообщения от клиента и запись в БД: {}", message);
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
        messageService.deleteById(id);
        Message message = messageService.getById(id).orElse(null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Скачивание файла по id
     */
    @PostMapping("/open-id")
    public ResponseEntity<Message> findById(@RequestBody DownloadHistory downloadHistory) {
        Optional<Message> message = messageService.getById(downloadHistory.getId());
        if (message.isPresent()) {
            downloadHistory.setFileName(message.get().getOriginFileName());
            downloadHistory.setMessage(message.get());
            Message messageResponse = downloadHistoryService.save(downloadHistory).getMessage();
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    /**
     * Получение файла по имени
     */
    @PostMapping("/open-name")
    public ResponseEntity<Message> findByName(@RequestBody DownloadHistory downloadHistory) throws UnsupportedEncodingException {
        String name = downloadHistory.getFileName();
        name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        Optional<Message> message = messageService.getByName(name);
        if (message.isPresent()) {
            downloadHistory.setFileName(name);
            downloadHistory.setId(message.get().getId());
            downloadHistory.setMessage(message.get());
            Message messageResponse = downloadHistoryService.save(downloadHistory).getMessage();
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
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
