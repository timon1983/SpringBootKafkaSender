package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.exception.AuthorizationJwtTokenException;
import com.example.uisbks.exception.ErrorLoginException;
import com.example.uisbks.exception.SuccessLoginException;
import com.example.uisbks.exception.NoIdException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Контроллер для обработки искключений NoIdException
 */
@ControllerAdvice
public class ClientExceptionHandlerController {

    @ExceptionHandler(NoIdException.class)
    public String handleNotException(NoIdException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return JspPage.ERROR;
    }

    @ExceptionHandler(SuccessLoginException.class)
    public String getSuccessResultOfAuthentication(SuccessLoginException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return JspPage.SUCCESS_AUTH_INFO;
    }

    @ExceptionHandler(ErrorLoginException.class)
    public String getErrorResultOfAuthentication(ErrorLoginException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return JspPage.ERROR_AUTH_INFO;
    }

    @ExceptionHandler(AuthorizationJwtTokenException.class)
    public String getErrorAuthorizationJwtToken(AuthorizationJwtTokenException e, Model model){
        model.addAttribute("error", e.getMessage());
        return JspPage.LOGIN_PAGE;
    }

}
