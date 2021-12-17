package com.example.sbks.controller;

import com.example.sbks.dto.DTOInfoModel;
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
    public ResponseEntity<DTOInfoModel> handleNoSuchFileException(NoSuchDataFileException e) {
        DTOInfoModel dtoInfoModel = new DTOInfoModel();
        dtoInfoModel.setIsError(true);
        dtoInfoModel.setInfo(e.getMessage());
        return new ResponseEntity<>(dtoInfoModel, HttpStatus.OK);
    }
}
