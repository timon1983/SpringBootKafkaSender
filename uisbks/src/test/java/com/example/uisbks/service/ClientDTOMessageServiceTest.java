package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.dtomodel.DTORequestMessage;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientDTOMessageServiceTest {


    ClientDTOMessageService clientDTOMessageService = new ClientDTOMessageService("http://localhost:8086/api/sdk/%s");

    @Test
    void check_getDTOMessage_Should_Return_isCorrect_DTOMessage() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", new byte[]{1, 2, 3, 4, 5});
        DTORequestMessage dtoRequestMessage = new DTORequestMessage();
        dtoRequestMessage.setTitle("aaa");
        dtoRequestMessage.setFile(multipartFile);
        dtoRequestMessage.setAuthor("bbb");
        DTOMessage dtoMessage = clientDTOMessageService.getDTOMessage(dtoRequestMessage);

        assertNotNull(dtoMessage);
        assertEquals(dtoMessage.getTitle(), dtoRequestMessage.getTitle());
        assertEquals(dtoMessage.getAuthor(), dtoRequestMessage.getAuthor());
        assertEquals(dtoMessage.getSize(), dtoRequestMessage.getFile().getSize());
        assertEquals(dtoMessage.getOriginFileName(), dtoRequestMessage.getFile().getOriginalFilename());
        assertEquals(dtoMessage.getContent(), dtoRequestMessage.getFile().getBytes());
    }

    @Test
    void check_getDTODownloadHistoryByName_Should_Return_DTODownloadHistory_By_FileName() {
        String name = "abc.txt";
        String ip = "0.0.0.0.1";
        DTODownloadHistory downloadHistory = clientDTOMessageService.getDTODownloadHistoryByName(name, ip);

        assertNotNull(downloadHistory);
        assertEquals(downloadHistory.getFileName(), name);
        assertEquals(downloadHistory.getIpUser(), ip);
        assertNull(downloadHistory.getId());
    }

    @Test
    void check_getDTODownloadHistoryById_Should_Return_DTODownloadHistory_By_FileId() {
        Long id = 5L;
        String ip = "0.0.0.0.1";
        DTODownloadHistory downloadHistory = clientDTOMessageService.getDTODownloadHistoryById(id, ip);

        assertNotNull(downloadHistory);
        assertEquals(downloadHistory.getId(), id);
        assertEquals(downloadHistory.getIpUser(), ip);
        assertNull(downloadHistory.getFileName());
    }

    @Test
    void check_getUrl_Should_Return_Url() {
        String endPoint = "abc.txt";
        String url = clientDTOMessageService.getUrl(endPoint);

        assertNotNull(url);
        assertEquals(url, "http://localhost:8086/api/sdk/abc.txt");
    }
}