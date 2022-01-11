package com.example.uisbks.exception;

/**
 * Исключение при ошибке валидации токена
 */
public class AuthorizationJwtTokenException extends RuntimeException{
    public AuthorizationJwtTokenException(String message) {
        super(message);
    }
}
