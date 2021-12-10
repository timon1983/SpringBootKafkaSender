package com.example.uisbks.controller;


import com.example.uisbks.dtomodel.DTODownloadClientInfo;
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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Контроллер для работы с сообщениями от клиента
 */
@Controller
@RequestMapping("/create")
@RequiredArgsConstructor
public class ClientMessageController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    // TODO: 08.12.2021 singleton prototype
    private final RestTemplate restTemplate;
    private final FileHandling fileHandling;
    //private final ClientMessageService clientMessageService;

    @GetMapping
    public String getCreatePage() {
        return "message-insert-form";
    }

    /**
     * Загрузка файла с UI
     */
    @PostMapping
    public String createMessage(MultipartHttpServletRequest request) throws ServletException,
            IOException, URISyntaxException {
        log.info("Получение сообщения от клиента");
        MultipartFile multipartFile = request.getFile("file");
        if (multipartFile != null && !multipartFile.isEmpty()) {
//            File file = fileHandling.convertMultiPartFileToFile(multipartFile.getOriginalFilename(),
//                    multipartFile.getBytes());
//            log.info("Загрузка файла {} в хранилище S3", file.getName());
//            clientMessageService.saveMessageToS3(file);
            URI uri = new URI("http://localhost:8085/api/sdk/create");
            //HttpEntity<DTOMessage> messageRequest = new HttpEntity<>(getDTOMessage(request, multipartFile));
            DTOMessage dtoMessage = getDTOMessage(request, multipartFile);
            log.info("Отправка данных по файлу {} в БД", multipartFile.getOriginalFilename());
            restTemplate.postForObject(uri, dtoMessage, DTOMessage.class);
        } else {
            log.error("Нет файла для загрузки");
        }
        return "message-insert-form";
    }

    /**
     * Получение списка всех загруженных файлов
     */
    @GetMapping("/files")
    public String getAllFiles(Model model) {
        log.info("Получение списка загруженных файлов");
        var url = "http://localhost:8085/api/sdk/files";
        List<DTOMessage> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        return "filesj";
    }

    /**
     * Удаление файла по id
     */
    @PostMapping("/file-delete")
    public String deleteFileById(HttpServletRequest request, Model model) {
        if (request.getParameter("id").equals("")) {
            log.error("Не введено id для удаления файла");
            model.addAttribute("error", "Введите id для удаления файла");
            return "error-page";
        }
        Long id = Long.parseLong(request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/delete";
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage != null && dtoMessage.getFileNameForS3() != null) {
            log.info("Файл {} удален", dtoMessage.getOriginFileName());
        } else {
            log.error("Данные о файле с Id={} в БД отсутствуют", id);
            model.addAttribute("error", "Данные о файле с Id = " + id + " в БД отсутствуют");
            return "error-page";
        }
        return "redirect:/create/files";
    }

    /**
     * Получение файла по id
     */
    @PostMapping("/open-file-id")
    public String openFileById(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        if (request.getParameter("id").equals("")) {
            log.error("Не введено id для открытия файла");
            model.addAttribute("error", "Введите id файла для открытия");
            return "error-page";
        }
        Long id = Long.parseLong(request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/open-id";
        return getURLToOpenFile(getDTODownloadClientInfo(id, request), url, model);
    }

    /**
     * Получение файла по имени
     */
    @PostMapping("/open-file-name")
    public String openFileByName(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        if (request.getParameter("name").equals("")) {
            log.error("Не введено name для открытия файла");
            model.addAttribute("error", "Введите name файла для открытия");
            return "error-page";
        }
        var name = request.getParameter("name");
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        var url = "http://localhost:8085/api/sdk/open-name";
        return getURLToOpenFile(getDTODownloadClientInfo(name, request), url, model);
    }

    /**
     * Отправка сообщения в SBKC
     */
    @PostMapping("/send")
    public String sendFile(HttpServletRequest request) {
        var name = request.getParameter("name");
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        var url = "http://localhost:8085/api/sdk/send-file";
        restTemplate.postForObject(url, name, DTOMessage.class);
        log.info("Сообщение отправлено в SBKC");
        return "redirect:/create/files";
    }

    /**
     * Загрузка файла по заданному атрибуту
     */
    public String getURLToOpenFile(Object attribute, String url, Model model) throws UnsupportedEncodingException {
        DTOMessage dtoMessage = restTemplate.postForObject(url, attribute, DTOMessage.class);
        if (dtoMessage != null && dtoMessage.getFileNameForS3() != null) {
            var fileNameS3 = dtoMessage.getFileNameForS3();
            fileNameS3 = URLEncoder.encode(fileNameS3, StandardCharsets.UTF_8);
            log.info("Файл {} получен", dtoMessage.getOriginFileName());
            return "redirect:https://d2lzjz6kkt1za6.cloudfront.net/" + fileNameS3;
        } else {
            var decodeAttribute = URLDecoder.decode(String.valueOf(attribute), StandardCharsets.UTF_8.name());
            log.error("Данные о файле с атрибутом {} в БД отсутствуют", decodeAttribute);
            model.addAttribute("error", "Данные о файле с атрибутом \"" + decodeAttribute
                    + "\" в БД отсутствуют");
            return "error-page";
        }
    }

    /**
     * Формирование объекта DTOMessage
     */
    private DTOMessage getDTOMessage(MultipartHttpServletRequest request, MultipartFile multipartFile)
            throws ServletException, IOException {
        return DTOMessage.builder()
                .title(request.getParameter("title"))
                .size(request.getPart("file").getSize())
                .dateOfCreate(LocalDateTime.now().withNano(0))
                .author(request.getParameter("author"))
                .originFileName(multipartFile.getOriginalFilename())
                .fileNameForS3(String.valueOf(System.currentTimeMillis()))
                .contentType(request.getPart("file").getContentType())
                .content(multipartFile.getBytes())
                .build();
    }

    /**
     * Формирование объекта DTODownloadClientInfo
     */
    private DTODownloadClientInfo getDTODownloadClientInfo(Object id, HttpServletRequest request) {
        Long idFile = null;
        String fileName = null;
        if (id instanceof Long) {
            idFile = (Long) id;
        }
        if (id instanceof String) {
            fileName = (String) id;
        }
        return DTODownloadClientInfo.builder()
                .idFile(idFile)
                .fileName(fileName)
                .ipUser(request.getRemoteAddr())
                .dateOfDownload(LocalDateTime.now().withNano(0))
                .build();
    }
}
