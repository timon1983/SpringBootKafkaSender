package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.DTODownloadClientInfo;
import com.example.uisbks.dtomodel.DTODownloadHistory;
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

@Controller
@RequestMapping("/downloaded")
@RequiredArgsConstructor
public class ClientDownloadHistory {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final RestTemplate restTemplate;

    @PostMapping
    public String getDownloadHistoryByFileId(HttpServletRequest request, Model model) {
        if (request.getParameter("id").equals("")) {
            log.error("Не введено id для открытия файла");
            model.addAttribute("error", "Введите id файла для открытия");
            return "error-page";
        }
        log.info("Получение истории скачивания файла " + request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/download-history";
        Long id = Long.valueOf(request.getParameter("id"));
        List<DTODownloadHistory> dtoDownloadClientInfos = restTemplate
                .postForObject(url, id, List.class);
        model.addAttribute("downloadList", dtoDownloadClientInfos);
        return "download-history";
    }
}
