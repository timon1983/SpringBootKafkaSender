package com.example.sbks.controller;

import com.example.sbks.exception.NoSuchDataFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NoSuchDataFileException.class)
    public ResponseEntity<String> handleNoSuchFileException(NoSuchDataFileException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }
}
