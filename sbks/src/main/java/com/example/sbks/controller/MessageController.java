package com.example.sbks.controller;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.dto.InfoDto;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.mapper.MapperForModel;
import com.example.sbks.service.DownloadHistoryService;
import com.example.sbks.service.MessageSenderService;
import com.example.sbks.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
    private final MapperForModel mapper = Mappers.getMapper(MapperForModel.class);

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
    public ResponseEntity<InfoDto> getAllMessages(ServletRequest servletRequest) {
        InfoDto infoDto = InfoDto.builder().object(messageService.getAll()).build();
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
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
        DownloadHistoryDto downloadHistoryDtoResponse = downloadHistoryService.saveById(downloadHistoryDto);
        InfoDto infoDto = mapper.downloadHistoryDtoToInfoDto(downloadHistoryDtoResponse);
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
        DownloadHistoryDto downloadHistoryDtoResponse = downloadHistoryService.saveByName(downloadHistoryDto);
        InfoDto infoDto = mapper.downloadHistoryDtoToInfoDto(downloadHistoryDtoResponse);
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }

    /**
     * Оправка файла в MessageSenderSender
     */
    @PostMapping("/send-file")
    public ResponseEntity<InfoDto> receiveMessageForSendToMessageSender(@RequestBody String name)
            throws UnsupportedEncodingException, URISyntaxException {
        name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        messageSenderService.sendMessage(name);
        return new ResponseEntity<>(new InfoDto(), HttpStatus.OK);
    }
}
