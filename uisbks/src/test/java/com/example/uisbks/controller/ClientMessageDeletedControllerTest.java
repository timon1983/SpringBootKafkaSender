package com.example.uisbks.controller;

import com.example.uisbks.service.ClientMessageDeletedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ClientMessageDeletedController.class)
@MockBean(ClientMessageDeletedService.class)
class ClientMessageDeletedControllerTest {

    @MockBean
    ClientMessageDeletedService clientMessageDeletedService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void check_getAllFiles_Should_Return_ListOfDeletedFiles() throws Exception {
        mockMvc.perform(get("/deleted").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("filesdeleted"));
    }

    @Test
    void check_deleteAll_Should_Delete_All_Deleted_Files() throws Exception {
        mockMvc.perform(get("/deleted/clean").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("filesdeleted"));
    }

    @Test
    void check_fullDelete_Should_FullDeleteFile() throws Exception {
        mockMvc.perform((get("/deleted/full/8")).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/deleted"));
    }

    @Test
    void restoreFile() throws Exception {
        mockMvc.perform((get("/deleted/restore/8")).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/deleted"));
    }
}