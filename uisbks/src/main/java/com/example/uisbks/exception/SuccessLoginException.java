package com.example.uisbks.exception;

/**
 * Исключение при успешной аутентификации
 */
public class SuccessLoginException extends RuntimeException{
    public SuccessLoginException(String message){
        super(message);
    }
}
