package com.example.sbks.exception;

public class NoSuchDataFileException extends RuntimeException{
    public NoSuchDataFileException(String message){
        super(message);
    }
}
