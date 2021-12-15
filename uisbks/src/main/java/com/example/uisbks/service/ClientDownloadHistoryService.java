package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientDownloadHistoryService {

    private final RestTemplate restTemplate;

    public String getAllDownloadHistory(HttpServletRequest request, Model model, Logger log){
        log.info("Получение истории скачивания файла " + request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/download-history";
        Long id = Long.valueOf(request.getParameter("id"));
        List<DTODownloadHistory> dtoDownloadClientInfos = restTemplate.postForObject(url, id, List.class);
        model.addAttribute("downloadList", dtoDownloadClientInfos);
        return "download-history";
    }
}
