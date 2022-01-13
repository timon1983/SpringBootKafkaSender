package com.example.sbks.service;

import com.example.sbks.dto.MessageDto;
import com.example.sbks.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    MessageServiceImpl messageService;

    @Test
    void check_save_Should_Return_MessageDto() {
        MessageDto messageDto = new MessageDto();
        lenient().when(messageService.save(messageDto)).thenReturn(messageDto);
    }

    @Test
    void check_getAll_Should_Return_ListOfMessageDto() {
        lenient().when(messageService.getAll()).thenReturn(Collections.emptyList());
    }

    @Test
    void check_deleteById() {
        messageService.deleteById(5L);
        verify(messageService).deleteById(5L);
    }

    @Test
    void check_getById_Should_Return_Optional() {
        lenient().when(messageService.getById(5L)).thenReturn(Optional.empty());
    }

    @Test
    void check_getByName_Should_Return_Optional() {
        lenient().when(messageService.getByName("abc.txt")).thenReturn(Optional.empty());
    }
}