package com.example.uisbks.utilcomponing;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class ClassBuilder {

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
            return null;
        }
    }

    /**
     * Формирование объекта DTODownloadHistory
     */
    public DTODownloadHistory getDTODownloadHistory(HttpServletRequest request) {
        String name = request.getParameter("name");
        if (name != null) {
            return DTODownloadHistory.builder()
                    .id(0)
                    .fileName(URLEncoder.encode(request.getParameter("name"), StandardCharsets.UTF_8))
                    .ipUser(request.getRemoteAddr())
                    .dateOfDownload(LocalDateTime.now().withNano(0))
                    .build();
        } else {
            return DTODownloadHistory.builder()
                    .id(Long.parseLong(request.getParameter("id")))
                    .fileName(null)
                    .ipUser(request.getRemoteAddr())
                    .dateOfDownload(LocalDateTime.now().withNano(0))
                    .build();
        }
    }
}
