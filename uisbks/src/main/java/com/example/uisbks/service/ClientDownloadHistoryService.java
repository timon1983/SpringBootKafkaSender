package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.JspPage;
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
    private final ClientDTOMessageService clientDTOMessageService;

    public String getAllDownloadHistory(HttpServletRequest request, Model model, Logger log) {
        log.info("Получение истории скачивания файла " + request.getParameter("id"));
        Long id = Long.valueOf(request.getParameter("id"));
        List<DTODownloadHistory> dtoDownloadClientInfos =
                restTemplate.postForObject(clientDTOMessageService.getUrl("download-history"), id, List.class);
        model.addAttribute("downloadList", dtoDownloadClientInfos);
        return JspPage.DOWNLOAD_HISTORY;
    }
}
