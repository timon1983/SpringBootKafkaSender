package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
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
    public DTOMessage getDTOMessage(MultipartHttpServletRequest request)
            throws ServletException, IOException {
        MultipartFile multipartFile = request.getFile("file");
        if (multipartFile != null && !multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
            var fileName = multipartFile.getOriginalFilename();
            return DTOMessage.builder()
                    .title(request.getParameter("title"))
                    .size(request.getPart("file").getSize())
                    .dateOfCreate(LocalDateTime.now().withNano(0))
                    .author(request.getParameter("author"))
                    .originFileName(fileName)
                    .fileNameForS3(String.join(".",
                            String.valueOf(System.currentTimeMillis()),
                            fileName.substring(fileName.lastIndexOf(".") + 1)))
                    .contentType(request.getPart("file").getContentType())
                    .content(multipartFile.getBytes())
                    .build();
        } else {
            throw new NoIdException("Файл не выбран");
        }
    }

    /**
     * Формирование объекта DTODownloadHistory по имени
     */
    public DTODownloadHistory getDTODownloadHistoryByName(HttpServletRequest request) {
        return DTODownloadHistory.builder()
                .id(null)
                .fileName(URLEncoder.encode(request.getParameter("name"), StandardCharsets.UTF_8))
                .ipUser(request.getRemoteAddr())
                .dateOfDownload(LocalDateTime.now().withNano(0))
                .build();
    }

    /**
     * Формирование объекта DTODownloadHistory по ID
     */
    public DTODownloadHistory getDTODownloadHistoryById(HttpServletRequest request) {
        return DTODownloadHistory.builder()
                .id(Long.parseLong(request.getParameter("id")))
                .fileName(null)
                .ipUser(request.getRemoteAddr())
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

