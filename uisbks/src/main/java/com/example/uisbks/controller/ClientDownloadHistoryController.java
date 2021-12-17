package com.example.uisbks.controller;

import com.example.uisbks.exception.NoIdException;
import com.example.uisbks.service.ClientDownloadHistoryService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер для работы с историей загрузок
 */
@Controller
@RequestMapping("/downloaded")
@RequiredArgsConstructor
public class ClientDownloadHistoryController {

    private final static Logger log = LogManager.getLogger(ClientDownloadHistoryController.class);
    private final ClientDownloadHistoryService clientDownloadHistoryService;

    /**
     * Получение истории скачивания файла по его id
     */
    @PostMapping
    public String getDownloadHistoryByFileId(HttpServletRequest request, Model model) {
        if (request.getParameter("id").isBlank()) {
            throw new NoIdException("Введите id для получения истории загрузки файла");
        }
        return clientDownloadHistoryService.getAllDownloadHistory(request, model, log);
    }
}
