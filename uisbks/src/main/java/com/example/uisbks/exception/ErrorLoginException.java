package com.example.uisbks.exception;

/**
 * Исключение при ошибке аутентификации
 */
public class ErrorLoginException extends RuntimeException{
    public ErrorLoginException(String message){
        super(message);
    }
}
