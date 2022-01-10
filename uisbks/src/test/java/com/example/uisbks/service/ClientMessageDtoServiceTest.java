package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DownloadHistoryDto;
import com.example.uisbks.dtomodel.MessageDto;
import com.example.uisbks.dtomodel.RequestMessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientMessageDtoServiceTest {


    ClientDTOMessageService clientDTOMessageService = new ClientDTOMessageService("http://localhost:8086/api/sdk/%s");


    @Test
    void check_getDTOMessage_Should_Return_isCorrect_DTOMessage() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", new byte[]{1, 2, 3, 4, 5});
        RequestMessageDto requestMessageDto = new RequestMessageDto();
        requestMessageDto.setTitle("aaa");
        requestMessageDto.setFile(multipartFile);
        requestMessageDto.setAuthor("bbb");
        MessageDto messageDto = clientDTOMessageService.getDTOMessage(requestMessageDto);

        assertNotNull(messageDto);
        assertEquals(messageDto.getTitle(), requestMessageDto.getTitle());
        assertEquals(messageDto.getAuthor(), requestMessageDto.getAuthor());
        assertEquals(messageDto.getSize(), requestMessageDto.getFile().getSize());
        assertEquals(messageDto.getOriginFileName(), requestMessageDto.getFile().getOriginalFilename());
        assertEquals(messageDto.getContent(), requestMessageDto.getFile().getBytes());
    }

    @Test
    void check_getDTODownloadHistoryByName_Should_Return_DTODownloadHistory_By_FileName() {
        String name = "abc.txt";
        String ip = "0.0.0.0.1";
        DownloadHistoryDto downloadHistory = clientDTOMessageService.getDTODownloadHistoryByName(name, ip);

        assertNotNull(downloadHistory);
        assertEquals(downloadHistory.getFileName(), name);
        assertEquals(downloadHistory.getIpUser(), ip);
        assertNull(downloadHistory.getId());
    }

    @Test
    void check_getDTODownloadHistoryById_Should_Return_DTODownloadHistory_By_FileId() {
        Long id = 5L;
        String ip = "0.0.0.0.1";
        DownloadHistoryDto downloadHistory = clientDTOMessageService.getDTODownloadHistoryById(id, ip);

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