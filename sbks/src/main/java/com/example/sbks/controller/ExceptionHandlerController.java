package com.example.sbks.controller;

import com.example.sbks.dto.InfoDto;
import com.example.sbks.exception.DuplicateEmailException;
import com.example.sbks.exception.JwtAuthenticationException;
import com.example.sbks.exception.NoSuchDataFileException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Контроллер для обработки исключений
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NoSuchDataFileException.class)
    public ResponseEntity<InfoDto> sendToUiNoSuchFileException(NoSuchDataFileException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<InfoDto> sendToUiJwtAuthenticationException(JwtAuthenticationException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<InfoDto> sendToUiJwtAuthenticationException(JwtException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<InfoDto> sendToUiJwtAuthenticationException(IllegalArgumentException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<InfoDto> sendToUiDuplicateEmailException(DuplicateEmailException e){
        return getResponseEntity(e);
    }

    private ResponseEntity<InfoDto> getResponseEntity(Exception e) {
        InfoDto infoDto = InfoDto.builder()
                .info(e.getMessage())
                .isError(true)
                .build();
        return ResponseEntity.ok(infoDto);
    }
}
