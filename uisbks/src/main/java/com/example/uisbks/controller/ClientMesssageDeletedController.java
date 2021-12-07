package com.example.uisbks.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/deleted")
@RequiredArgsConstructor
public class ClientMesssageDeletedController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Получение списка удаленных файлов
     */
    @GetMapping
    public String getAllFiles(Model model) {
        log.info("Получение списка удаленных файлов");
        String url = "http://localhost:8085/api/sdk/files-deleted";
        List<Object> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        return "filesdeleted";
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/clean")
    public String deleteAll(Model model) {
        String url = "http://localhost:8085/api/sdk/files-clean";
        List<Object> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        log.info("Список удаленных файлов очищен");
        return "filesdeleted";
    }
}
