package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс со вспомогательными методами
 */
@Component
@RequiredArgsConstructor
public class UtilityMethods {

    private final RestTemplate restTemplate;

    /**
     * Метод для выполнения операций с файлами в корзине(полное удаление, восстановление)
     */
    protected String doOperationWithDeletedFile(String urlEndPoint, String action, HttpServletRequest request,
                                                Model model, Logger log) {
        if (request.getParameter("id").equals("")) {
            return checkingForId(model, log);
        }
        Long id = Long.parseLong(request.getParameter("id"));
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage != null && dtoMessage.getFileNameForS3() != null) {
            log.info("Файл {} {}", dtoMessage.getOriginFileName(), action);
        } else {
            log.error("Файл с id={} в БД не найден", id);
            model.addAttribute("error", String.format("Файл с id=%d в БД не найден", id));
            return "error-page";
        }
        return "redirect:/deleted";
    }

    /**
     * Метод для выполнения оперций над списком файлов в корзине(получение списка, очистка корзины)
     */
    protected void doOperationWithListOfDeletedFile(Model model, String urlEndPoint) {
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        List<DTOMessage> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
    }

    /**
     * Метод для проверки поля ввода ID на "null"
     */
    protected String checkingForId(Model model, Logger log) {
        log.error("Не введено id для восстановления файла");
        model.addAttribute("error", "Введите id для удаления файла");
        return "error-page";
    }

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
