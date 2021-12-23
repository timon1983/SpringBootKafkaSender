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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;


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
        log.info("Получение запроса на список удаленных файлов");
        List<LinkedHashMap<String, Object>> dtoMessages =
                clientMessageDeletedService.doOperationWithListOfDeletedFile("files-deleted");
        model.addAttribute("listOfFiles", dtoMessages);
        return JspPage.FILE_LIST_DELETED;
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/clean")
    public String deleteAll(Model model) {
        log.info("Получение запроса на очистку списка удаленных файлов");
        List<LinkedHashMap<String, Object>> dtoMessages =
                clientMessageDeletedService.doOperationWithListOfDeletedFile("files-clean");
        model.addAttribute("listOfFiles", dtoMessages);
        log.info("Список удаленных файлов очищен");
        return JspPage.FILE_LIST_DELETED;
    }

    /**
     * Удаление файла на совсем по его id
     */
    @GetMapping("/full/{id}")
    public String fullDelete(@PathVariable Long id) throws IOException {
        clientMessageDeletedService.doOperationWithDeletedFile("full-delete", "полностью удален", id);
        return "redirect:/deleted";
    }

    /**
     * Восстановление файла
     */
    @GetMapping("/restore/{id}")
    public String restoreFile(@PathVariable Long id) throws IOException {
        clientMessageDeletedService.doOperationWithDeletedFile("restore-file", "восстановлен", id);
        return "redirect:/deleted";
    }
}
