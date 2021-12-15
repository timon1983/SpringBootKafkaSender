package com.example.uisbks.controller;


import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.exception.NoIdException;
import com.example.uisbks.service.ClientDTOMessageService;
import com.example.uisbks.service.ClientMessageService;
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
import java.net.URISyntaxException;


/**
 * Контроллер для работы с сообщениями от клиента
 */
@Controller
@RequestMapping("/create")
@RequiredArgsConstructor
public class ClientMessageController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final ClientMessageService clientMessageService;
    private final ClientDTOMessageService clientDTOMessageService;

    @GetMapping
    public String getCreatePage() {
        return "message-insert-form";
    }

    /**
     * Загрузка файла с UI
     */
    @PostMapping
    public String createMessage(MultipartHttpServletRequest request) throws URISyntaxException, ServletException, IOException {
        log.info("Получение сообщения от клиента");
        DTOMessage dtoMessage = clientDTOMessageService.getDTOMessage(request);
        return clientMessageService.doOperationToSaveFiles(dtoMessage, log);
    }

    /**
     * Получение списка всех загруженных файлов
     */
    @GetMapping("/files")
    public String getAllFiles(Model model) {
        return clientMessageService.getListOfFiles(model, log);
    }

    /**
     * Удаление файла по id
     */
    @PostMapping("/file-delete")
    public String deleteFileById(HttpServletRequest request, Model model) {
        if (request.getParameter("id").isBlank()) {
            throw new NoIdException("Введите id для удаления файла");
        }
        return clientMessageService.doOperationToDeleteFiles("delete", request, log);
    }

    /**
     * Получение файла по id
     */
    @PostMapping("/open-file-id")
    public String openFileById(HttpServletRequest request, Model model) {
        if (request.getParameter("id").isBlank()) {
            throw new NoIdException("Введите id для открытия файла");
        }
        DTODownloadHistory downloadHistory = clientDTOMessageService.getDTODownloadHistoryById(request);
        return clientMessageService.doOperationWithFilesForOpenByIdOrName("open-id", downloadHistory, model, log);

    }

    /**
     * Получение файла по имени
     */
    @PostMapping("/open-file-name")
    public String openFileByName(HttpServletRequest request, Model model) {
        if (request.getParameter("name").isBlank()) {
            throw new NoIdException("Введите имя для открытия файла");
        }
        DTODownloadHistory downloadHistory = clientDTOMessageService.getDTODownloadHistoryByName(request);
        return clientMessageService.doOperationWithFilesForOpenByIdOrName("open-name", downloadHistory, model, log);
    }

    /**
     * Отправка сообщения в SBKC
     */
    @PostMapping("/send")
    public String sendFile(HttpServletRequest request, Model model) {
        return clientMessageService.doOperationToSendFiles("send-file", request, model, log);
    }
}
