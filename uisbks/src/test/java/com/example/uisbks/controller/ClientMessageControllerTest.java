package com.example.uisbks.controller;

import com.example.uisbks.service.ClientDTOMessageService;
import com.example.uisbks.service.ClientMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(ClientMessageController.class)
@MockBean(ClientDTOMessageService.class)
@MockBean(ClientMessageService.class)
//@ContextConfiguration()
class ClientMessageControllerTest {

    @MockBean
    ClientDTOMessageService clientDTOMessageService;
    @MockBean
    ClientMessageService clientMessageService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void check_getCreatePage_Should_Return_JspPageToInsertFile() throws Exception {

        mockMvc.perform(get("/create").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(view().name("message-insert-form"))
                .andExpect(status().isOk());
    }

    @Test
    void check_createMessage_Should_Return_JspPageToInsertFile() throws Exception {
        mockMvc.perform(post("/create")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("message-insert-form"));
    }

    @Test
    void check_getAllFiles_Should_Return_JspPageFileList() throws Exception {
        mockMvc.perform(get("/create/files").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("files"));
    }

    @Test
    void check_deleteFileById_Should_Return_RedirectToFiles() throws Exception {
        mockMvc.perform(get("/create/file-delete/12").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/create/files"));
    }

    @Test
    void openFileById() {

    }

    @Test
    void openFileByName() {
    }

    @Test
    void sendFile() {
    }

}