package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.AuthDto;
import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.dtomodel.UserRegistrationDto;
import com.example.uisbks.service.ClientAuthService;
import com.example.uisbks.service.ClientRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для перехода на страницу аутентифиуации и стартовую страницу
 */
@Controller
@RequestMapping("UI")
@RequiredArgsConstructor
public class LoginController {

    private final ClientAuthService clientAuthService;
    private final ClientRegistrationService clientRegistrationService;

    /**
     * Переход на страницу аутентифиуации
     */
    @GetMapping("login")
    public String getLoginPage() {
        return JspPage.LOGIN_PAGE;
    }

    /**
     * Отправление данных для аутентификации
     */
    @PostMapping("login")
    public String sendLogin(@ModelAttribute AuthDto authDto) {
        clientAuthService.sendAuth(authDto);
        return JspPage.START_PAGE;
    }

    /**
     * Выход из аккаунта
     */
    @GetMapping("logout")
    public String sendLogout() {
        clientAuthService.sendOut();
        return JspPage.START_PAGE;
    }

    /**
     * Переход на страницу регистрации
     */
    @GetMapping("/registration")
    public String registrationUser(){
        return JspPage.REGISTRATION;
    }

    /**
     * Регистрация пользователя
     */
    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute UserRegistrationDto userRegistrationDto){
        clientRegistrationService.registrationNewUser(userRegistrationDto);
        return JspPage.REGISTRATION;
    }
}
