package com.example.uisbks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ClientDownloadHistoryServiceTest {

    @Mock
    ClientDownloadHistoryService clientDownloadHistoryService;

    @Test
    void getAllDownloadHistory() {
        lenient().when(clientDownloadHistoryService.getAllDownloadHistory(5L)).thenReturn(Collections.emptyList());
    }
}