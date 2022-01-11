package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DownloadHistoryDto;
import com.example.uisbks.dtomodel.MessageDto;
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
        MessageDto messageDto = new MessageDto();
        clientMessageService.doOperationToSaveFiles(messageDto);
        verify(clientMessageService).doOperationToSaveFiles(messageDto);
    }

    @Test
    void check_getListOfFiles_Should_Return_ListOfFiles() {
        lenient().when(clientMessageService.getListOfFiles()).thenReturn(Collections.emptyList());
    }

    @Test
    void check_doOperationWithFilesForOpenById_Should_Return_RedirectPage() {
        DownloadHistoryDto downloadHistoryDto = new DownloadHistoryDto();
        lenient().when(clientMessageService.doOperationWithFilesForOpenById(downloadHistoryDto))
                .thenReturn("/create/files");
    }

    @Test
    void check_doOperationWithFilesForOpenByName_Should_Return_RedirectPage(){
        DownloadHistoryDto downloadHistoryDto = new DownloadHistoryDto();
        lenient().when(clientMessageService.doOperationWithFilesForOpenByName(downloadHistoryDto))
                .thenReturn("/create/files");
    }

    @Test
    void check_doOperationToDeleteFiles() {
        clientMessageService.doOperationToDeleteFiles(5L);
        verify(clientMessageService).doOperationToDeleteFiles(5L);

    }

    @Test
    void check_doOperationToSendFiles() {
        clientMessageService.doOperationToSendFiles("abc.txt");
        verify(clientMessageService).doOperationToSendFiles("abc.txt");
    }
}