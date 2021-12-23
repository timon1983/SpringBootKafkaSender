package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientMessageServiceTest {

    @Mock
    ClientMessageService clientMessageService;

    @Test
    void doOperationToSaveFiles() throws URISyntaxException, IOException {
        DTOMessage dtoMessage = new DTOMessage();
        clientMessageService.doOperationToSaveFiles(dtoMessage);
        verify(clientMessageService).doOperationToSaveFiles(dtoMessage);
    }

    @Test
    void getListOfFiles() {
        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        lenient().when(clientMessageService.getListOfFiles()).thenReturn(list);
    }

    @Test
    void doOperationWithFilesForOpenByIdOrByName() {
        String urlEndPoint = "open-id";
        DTODownloadHistory dtoDownloadHistory = new DTODownloadHistory();
        lenient().when(clientMessageService.doOperationWithFilesForOpenByIdOrByName(urlEndPoint, dtoDownloadHistory))
                .thenReturn("/create/files");

    }

    @Test
    void doOperationToDeleteFiles() {
        clientMessageService.doOperationToDeleteFiles(5L);
        verify(clientMessageService).doOperationToDeleteFiles(5L);

    }

    @Test
    void doOperationToSendFiles() {
        clientMessageService.doOperationToSendFiles("abc.txt");
        verify(clientMessageService).doOperationToSendFiles("abc.txt");
    }
}