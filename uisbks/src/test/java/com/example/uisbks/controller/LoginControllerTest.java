package com.example.uisbks.controller;

import com.example.uisbks.service.ClientAuthService;
import com.example.uisbks.service.ClientRegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
@MockBean(classes = {
        ClientRegistrationService.class,
        ClientAuthService.class
})
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void check_getLoginPage_Should_Return_JspLoginPage() throws Exception {
        mockMvc.perform(get("/UI/login").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(view().name("login-page"))
                .andExpect(status().isOk());
    }

    @Test
    void check_sendLogin_Should_Return_JspStartPage() throws Exception {
        mockMvc.perform(post("/UI/login").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(view().name("start"))
                .andExpect(status().isOk());
    }

    @Test
    void check_sendLogout_Should_Return_JspStartPage() throws Exception {
        mockMvc.perform(get("/UI/logout").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(view().name("start"))
                .andExpect(status().isOk());
    }
}