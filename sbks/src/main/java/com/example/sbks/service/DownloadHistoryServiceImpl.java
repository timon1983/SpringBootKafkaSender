package com.example.sbks.service;

import com.example.sbks.dto.DownloadClientInfo;
import com.example.sbks.model.DownloadHistory;
import com.example.sbks.model.Message;
import com.example.sbks.repository.DownloadHistoryRepository;
import com.example.uisbks.controller.ClientMessageController;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadHistoryServiceImpl implements DownloadHistoryService {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final DownloadHistoryRepository downloadHistoryRepository;

    @Override
    public void save(DownloadClientInfo downloadClientInfo, Message message) {
        if (message.getFileNameForS3() != null) {
            DownloadHistory downloadHistory = DownloadHistory.builder()
                    .ipUser(downloadClientInfo.getIpUser())
                    .dateOfDownload(downloadClientInfo.getDateOfDownload())
                    .message(message)
                    .build();
            downloadHistoryRepository.save(downloadHistory);
        }

    }

    @Override
    public List<DownloadHistory> getAllById(Long id) {
        return downloadHistoryRepository.findAllByMessageId(id);
    }
}
