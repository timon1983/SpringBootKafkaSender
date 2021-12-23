package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.exception.NoIdException;
import com.example.uisbks.service.ClientDownloadHistoryService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        List<DTODownloadHistory> dtoDownloadClientInfos = clientDownloadHistoryService.getAllDownloadHistory(id);
        model.addAttribute("downloadList", dtoDownloadClientInfos);
        return JspPage.DOWNLOAD_HISTORY;
    }
}
