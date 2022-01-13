package com.example.sbks.controller;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.service.DownloadHistoryService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов по истории скачивания файлов
 */
@RestController
@RequestMapping("/api/sbk")
@RequiredArgsConstructor
public class MessageDownloadedController {

    private final static Logger log = LogManager.getLogger(MessageDownloadedController.class);
    private final DownloadHistoryService downloadHistoryService;

    /**
     * Обработка запроса для получения истории скачивания файла по его id
     */
    @PostMapping("/download-history")
    @PreAuthorize("hasAuthority('download-history:read')")
    public ResponseEntity<List<DownloadHistoryDto>> getDownloadHistory(@RequestBody Long id) {
        List<DownloadHistoryDto> downloadHistoryDtoList = downloadHistoryService.getAllById(id);
        log.info("Получение истории скачивания файла по id={}", id);
        return ResponseEntity.ok(downloadHistoryDtoList);
    }
}
