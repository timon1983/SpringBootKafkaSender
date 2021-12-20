package com.example.sbks.controller;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.exception.NoSuchDataFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Контроллер для обработки исключений NoSuchDataFileException
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NoSuchDataFileException.class)
    public ResponseEntity<DownloadHistoryDto> handleNoSuchFileException(NoSuchDataFileException e) {
        DownloadHistoryDto downloadHistoryDto = new DownloadHistoryDto();
        downloadHistoryDto.setIsError(true);
        downloadHistoryDto.setInfo(e.getMessage());
        return new ResponseEntity<>(downloadHistoryDto, HttpStatus.OK);
    }
}
