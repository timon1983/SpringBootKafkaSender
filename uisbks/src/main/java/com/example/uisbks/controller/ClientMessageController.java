package com.example.uisbks.controller;


import com.example.awsS3.service.ServiceS3;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.service.FileHandling;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/create")
@RequiredArgsConstructor
public class ClientMessageController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final FileHandling fileHandling;
    private final ServiceS3 serviceS3;

    @GetMapping
    public String getCreatePage() {

        return "message-insert-form";
    }

    /**
     * Загрузка файла с UI
     */
    @PostMapping
    public String createMessage(MultipartHttpServletRequest request) throws ServletException, IOException, URISyntaxException {
        log.info("Получение сообщения от клиента");
        MultipartFile multipartFile = request.getFile("file");
        if (!Objects.requireNonNull(multipartFile).isEmpty()) {
            File file = fileHandling.convertMultiPartFileToFile(Objects.requireNonNull(multipartFile));
            log.info("Загрузка файла {} в хранилище S3", file.getName());
            serviceS3.upload(file);
            DTOMessage message = DTOMessage.builder()
                    .title(request.getParameter("title"))
                    .size(request.getPart("file").getSize())
                    .dateOfCreate(LocalDate.now())
                    .timeOfCreate(LocalTime.now().withNano(0))
                    .author(request.getParameter("author"))
                    .originFileName(multipartFile.getOriginalFilename())
                    .fileNameForS3(file.getName())
                    .contentType(request.getPart("file").getContentType())
                    .build();
            String url = "http://localhost:8085/api/sdk/create";
            URI uri = new URI(url);
            HttpEntity<DTOMessage> messageRequest = new HttpEntity<>(message);
            log.info("Отправка данных по файлу {} в БД", file.getName());
            DTOMessage dtoMessage = restTemplate.postForObject(uri, messageRequest, DTOMessage.class);
            System.out.println(dtoMessage);
            return "message-insert-form";
        } else {
            log.error("Нет файла для загрузки");
            return "message-insert-form";
        }
    }

    /**
     * Получение списка всех загруженных файлов
     */
    @GetMapping("/files")
    public String getAllFiles(Model model) {
        log.info("Получение списка загруженных файлов");
        String url = "http://localhost:8085/api/sdk/files";
        List<Object> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        return "filesj";
    }

    /**
     * Удаление файла по id
     */
    @PostMapping("/file-delete")
    public String deleteFileById(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        String url = "http://localhost:8085/api/sdk/delete";
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage != null && dtoMessage.getFileNameForS3() != null) {
            log.info("Файл {} удален", dtoMessage.getOriginFileName());
            serviceS3.delete(dtoMessage.getFileNameForS3());
        } else {
            log.error("Данные о файле с Id={} в БД отсутствуют", id);
        }
        return "redirect:/create/files";
    }

    /**
     * Получение файла по id
     */
    @PostMapping("/open-file-id")
    public String openFileById(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        String url = "http://localhost:8085/api/sdk/open-id";
        return getFile(id, url);
    }

    /**
     * Получение файла по имени
     */
    @PostMapping("/open-file-name")
    public String openFileByName(HttpServletRequest request) {
        String name = request.getParameter("name");
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String url = "http://localhost:8085/api/sdk/open-name";
        return getFile(name, url);
    }

    /**
     * Загрузка файла по заданному атрибуту
     */
    public String getFile(Object attribute, String url) {
        DTOMessage dtoMessage = restTemplate.postForObject(url, attribute, DTOMessage.class);
        if (dtoMessage != null && dtoMessage.getFileNameForS3() != null) {
            String fileNameS3 = dtoMessage.getFileNameForS3();
            fileNameS3 = URLEncoder.encode(fileNameS3, StandardCharsets.UTF_8);
            log.info("Файл {} получен", dtoMessage.getOriginFileName());
            return "redirect:https://d2lzjz6kkt1za6.cloudfront.net/" + fileNameS3;
        } else {
            log.error("Данные о файле с атрибутом {} в БД отсутствуют", attribute);
            return "redirect:/create/files";
        }
    }
}
