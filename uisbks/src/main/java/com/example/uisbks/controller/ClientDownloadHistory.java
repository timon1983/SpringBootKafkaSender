package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.service.ClientMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Контроллер для работы с историей загрузок
 */
@Controller
@RequestMapping("/downloaded")
@RequiredArgsConstructor
public class ClientDownloadHistory {

    private final static Logger log = LogManager.getLogger(ClientDownloadHistory.class);
    private final RestTemplate restTemplate;
    private final ClientMessageService clientMessageService;

    /**
     * Получение истории скачивания файла по его id
     */
    @PostMapping
    public String getDownloadHistoryByFileId(HttpServletRequest request, Model model) {
        if (request.getParameter("id").equals("")) {
            clientMessageService.checkingForId(model, log);
        }
        log.info("Получение истории скачивания файла " + request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/download-history";
        Long id = Long.valueOf(request.getParameter("id"));
        List<DTODownloadHistory> dtoDownloadClientInfos = restTemplate.postForObject(url, id, List.class);
        model.addAttribute("downloadList", dtoDownloadClientInfos);
        return "download-history";
    }
}
