package com.example.sbks.controller;

import com.example.sbks.dto.InfoDto;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.service.DownloadHistoryService;
import com.example.sbks.service.MessageSenderService;
import com.example.sbks.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(MessageController.class)
//@MockBean(MessageService.class)
//@MockBean(MessageSenderService.class)

class MessageControllerTest {

//    @MockBean
//    DownloadHistoryService downloadHistoryService;
//    @MockBean
//    MessageService messageService;
//    @MockBean
//    MessageSenderService messageSenderService;
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;

    @Test
    void check_createMessage_Should_Return_ResponseEntity() throws Exception {
//        InfoDto infoDto = new InfoDto();
//        MessageDto messageDto = new MessageDto();
//        mockMvc.perform(post("/api/sdk/create").content(objectMapper.writeValueAsString(messageDto))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.isError").value(false));
    }

    @Test
    void getAllMessages() {
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