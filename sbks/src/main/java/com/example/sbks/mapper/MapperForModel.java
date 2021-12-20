package com.example.sbks.mapper;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.model.DownloadHistory;
import com.example.sbks.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface MapperForModel {

    @Mapping(target = "status", constant = "UPLOAD")
    Message dtoToMessage(MessageDto messageDto);

    MessageDto messageToDto(Message message);

    DownloadHistory dtoToDownloadHistory(DownloadHistoryDto downloadHistoryDto);

    DownloadHistoryDto downloadHistoryToDto(DownloadHistory downloadHistory);
}
