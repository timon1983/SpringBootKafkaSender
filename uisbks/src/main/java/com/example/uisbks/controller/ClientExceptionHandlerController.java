package com.example.uisbks.controller;

import com.example.uisbks.exception.NoIdException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientExceptionHandlerController {

    @ExceptionHandler(NoIdException.class)
    public String handleNotException(NoIdException e, Model model){
        model.addAttribute("error", e.getMessage());
        return "error-page";
    }
}
