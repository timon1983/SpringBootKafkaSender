package com.example.sbks.mapper;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.model.DownloadHistory;
import com.example.sbks.model.Message;
import org.mapstruct.Mapper;

@Mapper
public interface MapperForModel {
    MessageDto dtoToMessage(Message message);
    DownloadHistoryDto dtoToDownloadHistory(DownloadHistory downloadHistory);
}
