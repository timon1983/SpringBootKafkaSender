package com.example.sbks.service;

import com.example.sbks.dto.DownloadHistoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class DownloadHistoryServiceImplTest {

    @Mock
    DownloadHistoryServiceImpl downloadHistoryService;

    @Test
    void check_saveByName_Should_Return_DownloadHistoryDto() {
        lenient().when(downloadHistoryService.saveByName(new DownloadHistoryDto()))
                .thenReturn(new DownloadHistoryDto());
    }

    @Test
    void check_saveById_Should_Return_DownloadHistoryDto() {
        lenient().when(downloadHistoryService.saveByName(new DownloadHistoryDto()))
                .thenReturn(new DownloadHistoryDto());
    }

    @Test
    void check_getAllById_Should_Return_ListOfDownloadHistoryDto() {
        lenient().when(downloadHistoryService.getAllById(5L))
                .thenReturn(Collections.emptyList());
    }
}