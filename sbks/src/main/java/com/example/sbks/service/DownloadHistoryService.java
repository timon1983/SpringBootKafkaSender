package com.example.sbks.service;

import com.example.sbks.dto.DownloadClientInfo;
import com.example.sbks.model.DownloadHistory;
import com.example.sbks.model.Message;

import java.util.List;

public interface DownloadHistoryService {

   void save(DownloadClientInfo downloadClientInfo, Message message);

   List<DownloadHistory> getAllById(Long id);
}
