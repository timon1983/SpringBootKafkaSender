package com.example.sbks.controller;

import org.openapi.example.api.ApiApi;
import org.openapi.example.model.DownloadHistoryDto;
import org.openapi.example.model.InfoDto;
import org.openapi.example.model.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/g")
public class OpenApiTestController implements ApiApi {

    @Override

    public ResponseEntity<MessageDto> createMessage(MessageDto messageDto) {
        return new ResponseEntity<>(null);
    }

    @Override
    @GetMapping("/l")
    public ResponseEntity<List<MessageDto>> deleteAllMessages() {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InfoDto> deleteById(Long body) {
        return null;
    }

    @Override
    public ResponseEntity<InfoDto> deletingTheFileAtAll(Long body) {
        return null;
    }

    @Override
    public ResponseEntity<InfoDto> findById(DownloadHistoryDto downloadHistoryDto) {
        return null;
    }

    @Override
    public ResponseEntity<InfoDto> findByName(DownloadHistoryDto downloadHistoryDto) {
        return null;
    }

    @Override
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        return null;
    }

    @Override
    public ResponseEntity<List<MessageDto>> getAllMessages1() {
        return null;
    }

    @Override
    public ResponseEntity<List<DownloadHistoryDto>> getDownloadHistory(Long body) {
        return null;
    }

    @Override
    public ResponseEntity<DownloadHistoryDto> receiveMessageForSendToMessageSender(String body) {
        return null;
    }

    @Override
    public ResponseEntity<InfoDto> restoreMessageById(Long body) {
        return null;
    }
}
