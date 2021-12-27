package com.example.sbks.controller;

import com.example.sbks.dto.InfoDto;
import com.example.sbks.service.MessageDeletedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void check_deleteAllMessages() throws Exception {
        mockMvc.perform(get("/api/sdk/files-clean")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));
    }

    @Test
    void deletingTheFileAtAll() throws Exception {
        mockMvc.perform(post("/api/sdk/full-delete").content(objectMapper.writeValueAsString(5L))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new InfoDto())))
                .andExpect(jsonPath("$.isError").value(false));
    }

    @Test
    void restoreMessageById() throws Exception {
        mockMvc.perform(post("/api/sdk/restore-file").content(objectMapper.writeValueAsString(5L))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new InfoDto())))
                .andExpect(jsonPath("$.isError").value(false));
    }
}