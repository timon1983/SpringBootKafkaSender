package com.example.uisbks.controller;

import com.example.uisbks.service.ClientDownloadHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ClientDownloadHistoryController.class)
@MockBean(ClientDownloadHistoryService.class)
class ClientDownloadHistoryControllerTest {

    @MockBean
    ClientDownloadHistoryService clientDownloadHistoryService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void check_getDownloadHistoryByFileId_Should_Return_ListOfDownloadHistory() throws Exception {
        mockMvc.perform(get("/downloaded/?id=8").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("download-history"));
    }
}