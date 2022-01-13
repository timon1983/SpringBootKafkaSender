package com.example.sbks.controller;

import com.example.sbks.dto.InfoDto;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.service.MessageDeletedService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для обработки запросов по операциям с удаленными файлами
 */
@RestController
@RequestMapping("/api/sbk")
@RequiredArgsConstructor
public class MessageDeletedController {

    private final static Logger log = LogManager.getLogger(MessageDeletedController.class);
    private final MessageDeletedService messageDeletedService;

    /**
     * Получение списка удаленных файлов
     */
    @GetMapping("/files-deleted")
    @PreAuthorize("hasAuthority('deleted-message:read')")
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        log.info("Запрос в сервис на получение списка удаленных файлов");
        List<MessageDto> messageDtoList = messageDeletedService.getAll();
        return ResponseEntity.ok(messageDtoList);
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/files-clean")
    @PreAuthorize("hasAuthority('deleted-message:write')")
    public ResponseEntity<List<String>> deleteAllMessages() {
        log.info("Запрос на очистку списка удаленных файлов");
        List<String> fileNames = messageDeletedService.deleteAll();
        return ResponseEntity.ok(fileNames);
    }

    /**
     * Удаление файла на совсем
     */
    @PostMapping("/full-delete")
    @PreAuthorize("hasAuthority('deleted-message:write')")
    public ResponseEntity<InfoDto> deletingTheFileAtAll(@RequestBody Long id) {
        log.info("Запрос на полное удаление файла по id={}", id);
        InfoDto infoDto = InfoDto.builder()
                .info(messageDeletedService.fullDelete(id))
                .build();
        return ResponseEntity.ok(infoDto);
    }

    /**
     * Восстановление файла из корзины по его id
     */
    @PostMapping("/restore-file")
    @PreAuthorize("hasAuthority('deleted-message:write')")
    public ResponseEntity<InfoDto> restoreMessageById(@RequestBody Long id) {
        log.info("Запрос на восстановление файла по id={}", id);
        messageDeletedService.restoreMessage(id);
        return ResponseEntity.ok(InfoDto.builder().build());
    }
}
