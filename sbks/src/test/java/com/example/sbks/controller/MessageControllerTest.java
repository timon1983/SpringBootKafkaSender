package com.example.sbks.controller;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.dto.InfoDto;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.service.DownloadHistoryService;
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
@MockBean(DownloadHistoryService.class)
class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void check_createMessage_Should_Return_ResponseEntityOfInfoDto() throws Exception {
        mockMvc.perform(post("/api/sdk/create")
                .content(objectMapper.writeValueAsString(new MessageDto()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new InfoDto())))
                .andExpect(jsonPath("$.isError").value(false));
    }

    @Test
    void check_getAllMessages_Should_Return_ResponseEntityOfList() throws Exception {
        mockMvc.perform(get("/api/sdk/files")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));
    }

    @Test
    void check_deleteById_Should_Return_ResponseEntityOfInfoDto() throws Exception {
        mockMvc.perform(post("/api/sdk/delete").content(objectMapper.writeValueAsString(5L))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isError").value(false));
    }

    @Test
    void check_findById_Should_Return_ResponseEntityOfInfoDto() throws Exception {
        mockMvc.perform(post("/api/sdk/open-id")
                .content(objectMapper.writeValueAsString(new DownloadHistoryDto()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void check_findByName_Should_Return_ResponseEntityOfInfoDto() throws Exception {
        DownloadHistoryDto downloadHistoryDto = new DownloadHistoryDto();
        downloadHistoryDto.setFileName("abc.txt");
        mockMvc.perform(post("/api/sdk/open-name")
                .content(objectMapper.writeValueAsString(downloadHistoryDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void check_receiveMessageForSendToMessageSender_Should_Return_DownloadHistoryDto() throws Exception {
        String fileName = "abc.txt";
        mockMvc.perform(post("/api/sdk/send-file")
                .content(objectMapper.writeValueAsString(fileName))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new InfoDto())))
                .andExpect(jsonPath("$.isError").value(false));
    }
}
