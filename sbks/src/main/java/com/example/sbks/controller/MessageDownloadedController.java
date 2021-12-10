package com.example.sbks.controller;

import com.example.sbks.model.DownloadHistory;
import com.example.sbks.service.DownloadHistoryService;
import com.example.sbks.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sdk")
@RequiredArgsConstructor
public class MessageDownloadedController {

    private final static Logger log = LogManager.getLogger(MessageServiceImpl.class);
    private final DownloadHistoryService downloadHistoryService;

    @PostMapping("/download-history")
    public ResponseEntity<List<DownloadHistory>> getDownloadHistory(@RequestBody Long id) {
        List<DownloadHistory> downloadHistories = downloadHistoryService.getAllById(id);
        return new ResponseEntity<>(downloadHistories, HttpStatus.OK);
    }
}
