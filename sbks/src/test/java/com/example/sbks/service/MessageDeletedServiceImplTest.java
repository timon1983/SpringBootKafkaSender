package com.example.sbks.service;

import com.example.sbks.service.impl.MessageDeletedServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageDeletedServiceImplTest {

    @Mock
    MessageDeletedServiceImpl messageDeletedService;

    @Test
    void check_getAll_Should_Return_ListOfMessageDto() {
        lenient().when(messageDeletedService.getAll()).thenReturn(Collections.emptyList());
    }

    @Test
    void check_deleteAll_Should_Return_ListOfFileNames() {
        lenient().when(messageDeletedService.deleteAll()).thenReturn(Collections.emptyList());
    }

    @Test
    void check_fullDelete_Should_Return_FileName() {
        lenient().when(messageDeletedService.fullDelete(5L)).thenReturn("abc.txt");
    }

    @Test
    void check_restoreMessage() {
        messageDeletedService.restoreMessage(5L);
        verify(messageDeletedService).restoreMessage(5L);
    }
}