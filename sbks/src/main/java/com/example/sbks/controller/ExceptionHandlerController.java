package com.example.sbks.controller;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.dto.InfoDto;
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
    public ResponseEntity<InfoDto> handleNoSuchFileException(NoSuchDataFileException e) {
        InfoDto infoDto = new InfoDto();
        infoDto.setIsError(true);
        infoDto.setInfo(e.getMessage());
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }
}
