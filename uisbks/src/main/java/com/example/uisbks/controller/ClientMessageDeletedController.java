package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.exception.NoIdException;
import com.example.uisbks.service.ClientMessageDeletedService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Контроллер работы с файлами в корзине
 */
@Controller
@RequestMapping("/deleted")
@RequiredArgsConstructor
public class ClientMessageDeletedController {

    private final static Logger log = LogManager.getLogger(ClientMessageDeletedController.class);
    private final ClientMessageDeletedService clientMessageDeletedService;


    /**
     * Получение списка удаленных файлов
     */
    @GetMapping
    public String getAllFiles(Model model) {
        clientMessageDeletedService.doOperationWithListOfDeletedFile(model, "files-deleted");
        log.info("Получение списка удаленных файлов");
        return JspPage.FILE_LIST_DELETED;
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/clean")
    public String deleteAll(Model model) {
        clientMessageDeletedService.doOperationWithListOfDeletedFile(model, "files-clean");
        log.info("Список удаленных файлов очищен");
        return JspPage.FILE_LIST_DELETED;
    }

    /**
     * Удаление файла на совсем по его id
     */
    @PostMapping("/full")
    public String fullDelete(HttpServletRequest request) throws IOException {
        if (request.getParameter("id").isBlank()) {
            throw new NoIdException("Введите id для полного удаления файла");
        }
        return clientMessageDeletedService.doOperationWithDeletedFile("full-delete", "полностью удален",
                request);
    }

    /**
     * Восстановление файла
     */
    @GetMapping("/restore")
    public String restoreFile(HttpServletRequest request) throws IOException {
        if (request.getParameter("id").isBlank()) {
            throw new NoIdException("Введите id для восстановления файла");
        }
        return clientMessageDeletedService.doOperationWithDeletedFile("restore-file", "восстановлен",
                request);
    }
}
