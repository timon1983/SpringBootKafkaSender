package com.example.sbks.controller;

import com.example.sbks.dto.DTOInfoModel;
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
        return messageService.getById(id)
                .map(message -> new ResponseEntity<>(message, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.OK));
    }

    /**
     * Скачивание файла по id
     */
    @PostMapping("/open-id")
    public ResponseEntity<DTOInfoModel> findById(@RequestBody DownloadHistory downloadHistory) {
        DTOInfoModel dtoInfoModel = downloadHistoryService.saveById(downloadHistory);
        return new ResponseEntity<>(dtoInfoModel, HttpStatus.OK);
    }

    /**
     * Получение файла по имени
     */
    @PostMapping("/open-name")
    public ResponseEntity<DTOInfoModel> findByName(@RequestBody DownloadHistory downloadHistory)
            throws UnsupportedEncodingException {
        String fileName = downloadHistory.getFileName();
        downloadHistory.setFileName(URLDecoder.decode(fileName, StandardCharsets.UTF_8.name()));
        DTOInfoModel dtoInfoModel = downloadHistoryService.saveByName(downloadHistory);
        return new ResponseEntity<>(dtoInfoModel, HttpStatus.OK);
    }

    /**
     * Оправка файла в MessageSenderSender
     */
    @PostMapping("/send-file")
    public ResponseEntity<Message> receiveMessageForSendToMessageSender(@RequestBody String name)
            throws UnsupportedEncodingException {
        name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        return messageService.getByName(name)
                .map(message -> {
                    try {
                        messageSenderService.sendMessage(message);
                    } catch (URISyntaxException e) {
                        log.error("Ошибка отправки", e);
                    }
                    return new ResponseEntity<>(message, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.OK));
    }
}
