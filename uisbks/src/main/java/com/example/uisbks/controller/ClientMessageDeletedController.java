package com.example.uisbks.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * Контроллер работы с файлами в корзине
 */
@Controller
@RequestMapping("/deleted")
@RequiredArgsConstructor
public class ClientMessageDeletedController {

    private final static Logger log = LogManager.getLogger(ClientMessageDeletedController.class);
    private final UtilityMethods utilityMethods;


    /**
     * Получение списка удаленных файлов
     */
    @GetMapping
    public String getAllFiles(Model model) {
        utilityMethods.doOperationWithListOfDeletedFile(model, "files-deleted");
        log.info("Получение списка удаленных файлов");
        return "filesdeleted";
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/clean")
    public String deleteAll(Model model) {
        utilityMethods.doOperationWithListOfDeletedFile(model, "files-clean");
        log.info("Список удаленных файлов очищен");
        return "filesdeleted";
    }

    /**
     * Удаление файла на совсем по его id
     */
    @PostMapping("/full")
    public String fullDelete(HttpServletRequest request, Model model) {
        return utilityMethods.doOperationWithDeletedFile("full-delete", "полностью удален", request, model, log);
    }

    /**
     * Восстановление файла
     */
    @GetMapping("/restore")
    public String restoreFile(HttpServletRequest request, Model model) {
        return utilityMethods.doOperationWithDeletedFile("restore-file", "восстановлен", request, model, log);
    }

}
