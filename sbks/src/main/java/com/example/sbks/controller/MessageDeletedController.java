package com.example.sbks.controller;

import com.example.sbks.dto.InfoDto;
import com.example.sbks.dto.MessageDto;
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

    /**
     * Получение списка удаленных файлов
     */
    @GetMapping("/files-deleted")
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        List<MessageDto> messageDtoList = messageDeletedService.getAll();
        log.info("Контроллер.Запрос в сервис на получение списка удаленных файлов");
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/files-clean")
    public ResponseEntity<List<String>> deleteAllMessages() {
        List<String> fileNames = messageDeletedService.deleteAll();
        log.info("Контроллер.Запрос на очистку списка удаленных файлов");
        return new ResponseEntity<>(fileNames, HttpStatus.OK);
    }

    /**
     * Удаление файла на совсем
     */
    @PostMapping("/full-delete")
    public ResponseEntity<InfoDto> deletingTheFileAtAll(@RequestBody Long id) {
        log.info("Контроллер.Запрос на полное удаление файла по id={}", id);
        InfoDto infoDto = new InfoDto();
        infoDto.setInfo(messageDeletedService.fullDelete(id));
        return new ResponseEntity<>(infoDto,HttpStatus.OK);
    }

    /**
     * Восстановление файла из корзины по его id
     */
    @PostMapping("/restore-file")
    public ResponseEntity<InfoDto> restoreMessageById(@RequestBody Long id) {
        log.info("Контроллер.Запрос на восстановление файла по id={}", id);
        messageDeletedService.restoreMessage(id);
        return new ResponseEntity<>(new InfoDto(),HttpStatus.OK);
    }
}
