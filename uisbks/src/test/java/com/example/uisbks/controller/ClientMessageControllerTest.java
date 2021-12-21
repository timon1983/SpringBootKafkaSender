package com.example.uisbks.controller;

import com.example.uisbks.service.ClientDTOMessageService;
import com.example.uisbks.service.ClientMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.LinkedMultiValueMap;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        byte[] file1 = {2,52,3,5,55};
        var mockRequest = new MockMultipartHttpServletRequest();
        mockRequest.addFile(new MockMultipartFile("file", file1));
//        var dtoMessage = clientDTOMessageService.getDTOMessage(mockRequest);
//        when(clientDTOMessageService.getDTOMessage(mockRequest)).thenReturn(dtoMessage);
//        when(clientMessageService.doOperationToSaveFiles(dtoMessage)).thenReturn("message-insert-form");
        MockMultipartHttpServletRequestBuilder mockMultipartHttpServletRequestBuilder =
                (MockMultipartHttpServletRequestBuilder)multipart("http://localhost:9090/create").file(new MockMultipartFile("file", file1));
        ResultActions resultActions = mockMvc.perform(mockMultipartHttpServletRequestBuilder);
        resultActions.andExpect(view().name("message-insert-form"))
               .andExpect(status().isOk());

//        mockMvc.perform(multipart("/create").file(new MockMultipartFile("file", file)).)
//                .andExpect(view().name("message-insert-form"))
//                .andExpect(status().isOk());
    }

    @Test
    void check_getAllFiles_Should_Return_JspPageFileList() {

    }

    @Test
    void deleteFileById() {
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