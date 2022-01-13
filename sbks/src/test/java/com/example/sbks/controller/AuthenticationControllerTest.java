package com.example.sbks.controller;

import com.example.sbks.dto.AuthenticationDTO;
import com.example.sbks.repository.UserAuthRepository;
import com.example.sbks.security.JwtConfigurer;
import com.example.sbks.security.JwtTokenFilter;
import com.example.sbks.security.JwtTokenProvider;
import com.example.sbks.service.UserRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@MockBean(classes = {
        UserAuthRepository.class,
        JwtTokenProvider.class,
        JwtConfigurer.class,
        JwtTokenFilter.class,
        AuthenticationManager.class,
        UserRegistrationService.class
})
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void check_authenticate() throws Exception {
        AuthenticationDTO authenticationDTO = AuthenticationDTO.builder()
                .email("admin@mail.com")
                .password("admin")
                .build();
        mockMvc.perform(post("/api/auth/login")
                .content(objectMapper.writeValueAsString(authenticationDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        //             .andExpect(content().json(objectMapper.writeValueAsString(new InfoDto())));
    }

    @Test
    @WithMockUser
    void logout() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}