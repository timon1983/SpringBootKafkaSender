package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.DownloadHistoryDto;
import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.service.ClientDownloadHistoryService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    @GetMapping("")
    public String getDownloadHistoryByFileId(@RequestParam Long id, Model model) {
        log.info("Получение запроса на историю загрузки файла по id={}", id);
        List<DownloadHistoryDto> dtoDownloadClientInfos = clientDownloadHistoryService.getAllDownloadHistory(id);
        model.addAttribute("downloadList", dtoDownloadClientInfos);
        return JspPage.DOWNLOAD_HISTORY;
    }
}
