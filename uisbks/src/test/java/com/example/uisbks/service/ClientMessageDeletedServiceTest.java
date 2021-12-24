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
    void doOperationWithDeletedFile() throws IOException {
        clientMessageDeletedService.doOperationWithDeletedFile("full-delete", "полностью удален", 5L);
        verify(clientMessageDeletedService)
                .doOperationWithDeletedFile("full-delete", "полностью удален", 5L);
    }

    @Test
    void doOperationWithListOfDeletedFile() {
        lenient().when(clientMessageDeletedService.doOperationWithListOfDeletedFile("files-deleted"))
                .thenReturn(Collections.emptyList());
    }
}