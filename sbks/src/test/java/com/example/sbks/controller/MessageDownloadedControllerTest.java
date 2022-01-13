package com.example.sbks.controller;

import com.example.sbks.security.JwtConfigurer;
import com.example.sbks.security.JwtTokenFilter;
import com.example.sbks.service.DownloadHistoryService;
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

@WebMvcTest(MessageDownloadedController.class)
@MockBean(classes = {
        DownloadHistoryService.class,
        JwtConfigurer.class,
        JwtTokenFilter.class,
        AuthenticationManager.class
})
class MessageDownloadedControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser()
    void check_getDownloadHistory_Should_Return_ResponseEntityOfList() throws Exception {
        mockMvc.perform(post("/api/sdk/download-history")
                        .content(objectMapper.writeValueAsString(5L))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
//                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));
    }
}