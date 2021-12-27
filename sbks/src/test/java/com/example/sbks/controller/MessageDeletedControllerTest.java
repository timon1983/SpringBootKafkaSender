package com.example.sbks.controller;

import com.example.sbks.service.MessageDeletedService;
import com.example.sbks.service.MessageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageDeletedController.class)
@MockBean(MessageDeletedService.class)
class MessageDeletedControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void check_getAllMessages_Should_Return_ResponseEntityOfList() throws Exception {
        mockMvc.perform(get("/api/sdk/files-deleted")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));
    }

    @Test
    void check_deleteAllMessages() {
    }

    @Test
    void deletingTheFileAtAll() {
    }

    @Test
    void restoreMessageById() {
    }
}