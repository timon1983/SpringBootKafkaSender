package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DownloadHistoryDto;
import com.example.uisbks.dtomodel.MessageDto;
import com.example.uisbks.dtomodel.RequestMessageDto;
import com.example.uisbks.exception.NoIdException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * Вспомогательный сервис для обработки dto и генерации url
 */
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientDTOMessageService {

    @Value("${url-sbks}")
    private String url;

    /**
     * Формирование объекта DTOMessage
     */
    public MessageDto getDTOMessage(RequestMessageDto requestMessageDto)
            throws IOException {
        MultipartFile multipartFile = requestMessageDto.getFile();
        if (multipartFile != null && !multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
            var fileName = multipartFile.getOriginalFilename();
            return MessageDto.builder()
                    .title(requestMessageDto.getTitle())
                    .size(multipartFile.getSize())
                    .dateOfCreate(LocalDateTime.now().withNano(0))
                    .author(requestMessageDto.getAuthor())
                    .originFileName(fileName)
                    .fileNameForS3(String.join(".",
                            String.valueOf(System.currentTimeMillis()),
                            fileName.substring(fileName.lastIndexOf(".") + 1)))
                    .contentType(multipartFile.getContentType())
                    .content(multipartFile.getBytes())
                    .build();
        } else {
            throw new NoIdException("Файл не выбран");
        }
    }

    /**
     * Формирование объекта DTODownloadHistory по имени
     */
    public DownloadHistoryDto getDTODownloadHistoryByName(String name, String ip) {
        if (name.isBlank()) {
            throw new NoIdException(MessageFormat.format("Параметр {} не должен быть пустым", name));
        }
        return DownloadHistoryDto.builder()
                .fileName(URLEncoder.encode(name, StandardCharsets.UTF_8))
                .ipUser(ip)
                .dateOfDownload(LocalDateTime.now().withNano(0))
                .build();
    }

    /**
     * Формирование объекта DTODownloadHistory по ID
     */
    public DownloadHistoryDto getDTODownloadHistoryById(Long id, String ip) {
        if (id == 0) {
            throw new NoIdException("Введите id для открытия файла");
        }
        return DownloadHistoryDto.builder()
                .id(id)
                .fileName(null)
                .ipUser(ip)
                .dateOfDownload(LocalDateTime.now().withNano(0))
                .build();
    }

    /**
     * Формирование URL для запроса
     */
    public String getUrl(String urlEndPoint) {
        return String.format(url, urlEndPoint);
    }
}

