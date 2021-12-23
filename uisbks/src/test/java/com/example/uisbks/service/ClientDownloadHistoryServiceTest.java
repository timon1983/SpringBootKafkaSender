package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ClientDownloadHistoryServiceTest {

    @Mock
    ClientDownloadHistoryService clientDownloadHistoryService;

    @Test
    void getAllDownloadHistory() {
        List<DTODownloadHistory> list = new ArrayList<>();
        lenient().when(clientDownloadHistoryService.getAllDownloadHistory(5L)).thenReturn(list);
    }
}