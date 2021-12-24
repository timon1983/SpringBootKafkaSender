package com.example.sbks.controller;

import com.example.sbks.dto.InfoDto;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.service.DownloadHistoryServiceImpl;
import com.example.sbks.service.MessageSenderKafkaService;
import com.example.sbks.service.MessageServiceImpl;
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

@WebMvcTest(MessageController.class)
@MockBean(MessageServiceImpl.class)
@MockBean(MessageSenderKafkaService.class)
@MockBean(DownloadHistoryServiceImpl.class)
class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void check_createMessage_Should_Return_ResponseEntity() throws Exception {
        mockMvc.perform(post("/api/sdk/create").content(objectMapper.writeValueAsString(new MessageDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.isError").value(false));
    }

    @Test
    void getAllMessages() throws Exception {
        mockMvc.perform(get("/api/sdk/files").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));
    }

    @Test
    void deleteById() {

    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void receiveMessageForSendToMessageSender() {
    }
}