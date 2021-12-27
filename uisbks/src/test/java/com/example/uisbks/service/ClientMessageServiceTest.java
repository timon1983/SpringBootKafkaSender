package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientMessageServiceTest {

    @Mock
    ClientMessageService clientMessageService;

    @Test
    void check_doOperationToSaveFiles() throws URISyntaxException, IOException {
        DTOMessage dtoMessage = new DTOMessage();
        clientMessageService.doOperationToSaveFiles(dtoMessage);
        verify(clientMessageService).doOperationToSaveFiles(dtoMessage);
    }

    @Test
    void check_getListOfFiles_Should_Return_ListOfFiles() {
        lenient().when(clientMessageService.getListOfFiles()).thenReturn(Collections.emptyList());
    }

    @Test
    void check_doOperationWithFilesForOpenById_Should_Return_RedirectPage() {
        DTODownloadHistory dtoDownloadHistory = new DTODownloadHistory();
        lenient().when(clientMessageService.doOperationWithFilesForOpenById(dtoDownloadHistory))
                .thenReturn("/create/files");
    }

    @Test
    void check_doOperationWithFilesForOpenByName_Should_Return_RedirectPage(){
        DTODownloadHistory dtoDownloadHistory = new DTODownloadHistory();
        lenient().when(clientMessageService.doOperationWithFilesForOpenByName(dtoDownloadHistory))
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