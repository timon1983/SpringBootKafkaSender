package com.example.uisbks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientMessageDeletedServiceTest {

    @Mock
    ClientMessageDeletedService clientMessageDeletedService;

    @Test
    void check_fullDeleteOfFile() throws IOException {
        clientMessageDeletedService.fullDeleteOfFile(5L);
        verify(clientMessageDeletedService).fullDeleteOfFile(5L);
    }

    @Test
    void check_restoreFile() throws IOException {
        clientMessageDeletedService.restoreFile(5L);
        verify(clientMessageDeletedService).restoreFile(5L);
    }

    @Test
    void check_getListOfDeletedFile_Should_Return_ListOfFiles() {
        lenient().when(clientMessageDeletedService.getListOfDeletedFile())
                .thenReturn(Collections.emptyList());
    }

    @Test
    void check_cleanListOfDeletedFile_Should_Return_ListOfFileNames(){
        lenient().when(clientMessageDeletedService.cleanListOfDeletedFile())
                .thenReturn(Collections.emptyList());
    }
}