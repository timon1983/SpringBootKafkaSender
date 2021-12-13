package com.example.uisbks.controller;


import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Контроллер для работы с сообщениями от клиента
 */
@Controller
@RequestMapping("/create")
@RequiredArgsConstructor
public class ClientMessageController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final RestTemplate restTemplate;
    private final UtilityMethods utilityMethods;

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
        DTOMessage dtoMessage = utilityMethods.getDTOMessage(request);
        if (dtoMessage != null) {
            URI uri = new URI("http://localhost:8085/api/sdk/create");
            log.info("Отправка данных по файлу {} в БД", dtoMessage.getOriginFileName());
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
            utilityMethods.checkingForId(model, log);
        }
        Long id = Long.parseLong(request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/delete";
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage != null) {
            log.info("Файл {} удален", dtoMessage.getOriginFileName());
        } else {
            log.error("Данные о файле с Id={} в БД отсутствуют", id);
            model.addAttribute("error", String.format("Файл с id=%d в БД не найден", id));
            return "error-page";
        }
        return "redirect:/create/files";
    }

    /**
     * Получение файла по id
     */
    @PostMapping("/open-file-id")
    public String openFileById(HttpServletRequest request, Model model) {
        if (request.getParameter("id").equals("")) {
            utilityMethods.checkingForId(model, log);
        }
        Long id = Long.parseLong(request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/open-id";
        DTODownloadHistory downloadHistory = utilityMethods.getDTODownloadHistory(request);
        DTOMessage dtoMessage = restTemplate.postForObject(url, downloadHistory, DTOMessage.class);
        if (dtoMessage != null) {
            log.info("Файл {} получен", dtoMessage.getOriginFileName());
            return String.format("redirect:https://d2lzjz6kkt1za6.cloudfront.net/%s", dtoMessage.getFileNameForS3());
        } else {
            log.error("Данные о файле с id={} в БД отсутствуют", id);
            model.addAttribute("error", String.format("Файл с id=%d в БД не найден", id));
            return "error-page";
        }
    }

    /**
     * Получение файла по имени
     */
    @PostMapping("/open-file-name")
    public String openFileByName(HttpServletRequest request, Model model) {
        if (request.getParameter("name").equals("")) {
            utilityMethods.checkingForId(model, log);
        }
        var name = request.getParameter("name");
        var url = "http://localhost:8085/api/sdk/open-name";
        DTODownloadHistory downloadHistory = utilityMethods.getDTODownloadHistory(request);
        DTOMessage dtoMessage = restTemplate.postForObject(url, downloadHistory, DTOMessage.class);
        if (dtoMessage != null) {
            log.info("Файл {} получен", dtoMessage.getOriginFileName());
            return String.format("redirect:https://d2lzjz6kkt1za6.cloudfront.net/%s", dtoMessage.getFileNameForS3());
        } else {
            log.error("Данные о файле с именем {} в БД отсутствуют", name);
            model.addAttribute("error", String.format("Файл с именем %s в БД не найден", name));
            return "error-page";
        }
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
}
