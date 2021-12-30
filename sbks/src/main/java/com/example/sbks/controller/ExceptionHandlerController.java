package com.example.sbks.controller;

import com.example.sbks.dto.InfoDto;
import com.example.sbks.exception.JwtAuthenticationException;
import com.example.sbks.exception.NoSuchDataFileException;
import io.jsonwebtoken.JwtException;
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
        InfoDto infoDto = InfoDto.builder()
                .info(e.getMessage())
                .isError(true)
                .build();
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<InfoDto> handleJwtAuthenticationException(JwtAuthenticationException e){
        InfoDto infoDto = InfoDto.builder()
                .info(e.getMessage())
                .isError(true)
                .build();
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<InfoDto> handleJwtAuthenticationException(JwtException e){
        InfoDto infoDto = InfoDto.builder()
                .info(e.getMessage())
                .isError(true)
                .build();
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<InfoDto> handleJwtAuthenticationException(IllegalArgumentException e){
        InfoDto infoDto = InfoDto.builder()
                .info(e.getMessage())
                .isError(true)
                .build();
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }
}
