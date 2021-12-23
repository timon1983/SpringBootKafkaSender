package com.example.uisbks.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StartController.class)
class StartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void check_startProject_Should_Return_JspPageStart() throws Exception {
        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(view().name("start"))
                .andExpect(status().isOk());
    }
}