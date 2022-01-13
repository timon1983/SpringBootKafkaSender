package com.example.uisbks.exception;

/**
 * Исключение при вводе пароля
 */
public class PasswordInvalidateException extends RuntimeException{
    public PasswordInvalidateException(String message){
        super(message);
    }
}
