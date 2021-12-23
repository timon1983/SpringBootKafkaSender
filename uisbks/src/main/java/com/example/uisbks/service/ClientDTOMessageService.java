package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.dtomodel.DTORequestMessage;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientDTOMessageService {

    @Value("${url-sbks}")
    private String url;

    /**
     * Формирование объекта DTOMessage
     */
    public DTOMessage getDTOMessage(DTORequestMessage dtoRequestMessage)
            throws IOException {
        MultipartFile multipartFile = dtoRequestMessage.getFile();
        if (multipartFile != null && !multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
            var fileName = multipartFile.getOriginalFilename();
            return DTOMessage.builder()
                    .title(dtoRequestMessage.getTitle())
                    .size(multipartFile.getSize())
                    .dateOfCreate(LocalDateTime.now().withNano(0))
                    .author(dtoRequestMessage.getAuthor())
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
    public DTODownloadHistory getDTODownloadHistoryByName(String name, String ip) {
        if (name.isBlank()) {
            throw new NoIdException("Введите имя для открытия файла");
        }
        return DTODownloadHistory.builder()
                .id(null)
                .fileName(URLEncoder.encode(name, StandardCharsets.UTF_8))
                .ipUser(ip)
                .dateOfDownload(LocalDateTime.now().withNano(0))
                .build();
    }

    /**
     * Формирование объекта DTODownloadHistory по ID
     */
    public DTODownloadHistory getDTODownloadHistoryById(Long id, String ip) {
        if (id == 0) {
            throw new NoIdException("Введите id для открытия файла");
        }
        return DTODownloadHistory.builder()
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

