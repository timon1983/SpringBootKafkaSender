package com.example.sbks.mapper;

import com.example.sbks.dto.DownloadHistoryDto;
import com.example.sbks.dto.InfoDto;
import com.example.sbks.dto.MessageDto;
import com.example.sbks.model.DownloadHistory;
import com.example.sbks.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapperForModel {

    Message dtoToMessage(MessageDto messageDto);

    MessageDto messageToDto(Message message);

    DownloadHistory dtoToDownloadHistory(DownloadHistoryDto downloadHistoryDto);

    DownloadHistoryDto downloadHistoryToDto(DownloadHistory downloadHistory);

    @Mapping(target="info", source="downloadHistoryDto.fileName")
    InfoDto downloadHistoryDtoToInfoDto(DownloadHistoryDto downloadHistoryDto);
}
