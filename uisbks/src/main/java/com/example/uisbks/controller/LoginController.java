package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.AuthDto;
import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.service.ClientAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
@RequiredArgsConstructor
public class LoginController {

    private final ClientAuthService clientAuthService;

    @GetMapping
    public String getLoginPage(){
        return JspPage.LOGIN_PAGE;
    }

    @PostMapping
    public String sendLogin(@ModelAttribute AuthDto authDto){
        clientAuthService.sendAuth(authDto);
        return JspPage.START_PAGE;
    }
}
