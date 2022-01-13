package com.example.sbks.repository;

import com.example.sbks.model.DownloadHistory;
import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class DownloadHistoryRepositoryTest {

    @Autowired
    DownloadHistoryRepository downloadHistoryRepository;
    @Autowired
    MessageRepository messageRepository;
    DownloadHistory downloadHistory1;
    DownloadHistory downloadHistory2;
    Message message;

    @BeforeEach
    void getDownloadHistory() {
        message = Message.builder()
                .id(1L)
                .originFileName("abc.txt")
                .fileNameForS3("1234.txt")
                .author("timon")
                .title("file")
                .dateOfCreate(LocalDateTime.now())
                .size(45L)
                .contentType("txt")
                .status(Status.UPLOAD)
                .build();
        downloadHistory1 = DownloadHistory.builder()
                .fileName("abc.txt")
                .ipUser("0.0.0.1")
                .dateOfDownload(LocalDateTime.now())
                .message(message)
                .build();
        downloadHistory2 = DownloadHistory.builder()
                .fileName("abcd.txt")
                .ipUser("0.0.0.2")
                .dateOfDownload(LocalDateTime.now())
                .message(message)
                .build();
    }

    @Test
    void findAllByMessageId_ShouldReturnListOfDownloadHistory() {
        messageRepository.save(message);
        downloadHistoryRepository.save(downloadHistory1);
        downloadHistoryRepository.save(downloadHistory2);
        List<DownloadHistory> downloadHistoryList = downloadHistoryRepository.findAllByMessageId(1L,
                PageRequest.of(0, 10));

        assertNotNull(downloadHistoryList);
        assertEquals(downloadHistoryList.size(), 2);
    }
}