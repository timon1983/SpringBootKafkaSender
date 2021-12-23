package com.example.sbks.controller;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.dto.InfoDto;
import com.example.sbks.dto.MessageDto;
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
     * Получение сообщения от клиента
     */
    @PostMapping("/create")
    public ResponseEntity<InfoDto> createMessage(@RequestBody MessageDto messageDto) {
        log.info("Получение сообщения от клиента и запись в БД");
        messageService.save(messageDto);
        return new ResponseEntity<>(new InfoDto(), HttpStatus.OK);
    }

    /**
     * Получение списка всех загруженных файлов
     */
    @GetMapping("/files")
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        List<MessageDto> messageDtoList = messageService.getAll();
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    /**
     * Удаление файла по id
     */
    @PostMapping("/delete")
    public ResponseEntity<InfoDto> deleteById(@RequestBody Long id) {
        messageService.deleteById(id);
        return new ResponseEntity<>(new InfoDto(), HttpStatus.OK);
    }

    /**
     * Скачивание файла по id
     */
    @PostMapping("/open-id")
    public ResponseEntity<InfoDto> findById(@RequestBody DownloadHistoryDto downloadHistoryDto) {
        InfoDto infoDto = downloadHistoryService.saveById(downloadHistoryDto);
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }

    /**
     * Получение файла по имени
     */

    @PostMapping("/open-name")
    public ResponseEntity<InfoDto> findByName(@RequestBody DownloadHistoryDto downloadHistoryDto)
            throws UnsupportedEncodingException {
        String fileName = downloadHistoryDto.getFileName();
        downloadHistoryDto.setFileName(URLDecoder.decode(fileName, StandardCharsets.UTF_8.name()));
        InfoDto infoDto = downloadHistoryService.saveByName(downloadHistoryDto);
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }

    /**
     * Оправка файла в MessageSenderSender
     */
    @PostMapping("/send-file")
    public ResponseEntity<DownloadHistoryDto> receiveMessageForSendToMessageSender(@RequestBody String name)
            throws UnsupportedEncodingException, URISyntaxException {
        name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        messageSenderService.sendMessage(name);
        return new ResponseEntity<>(new DownloadHistoryDto(), HttpStatus.OK);
    }
}
