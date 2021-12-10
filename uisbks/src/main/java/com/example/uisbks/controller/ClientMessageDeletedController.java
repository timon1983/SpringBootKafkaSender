package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.service.ClientMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Контроллер работы с файлами в корзине
 */
@Controller
@RequestMapping("/deleted")
@RequiredArgsConstructor
public class ClientMessageDeletedController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final RestTemplate restTemplate;
    private final ClientMessageService clientMessageService;

    /**
     * Получение списка удаленных файлов
     */
    @GetMapping
    public String getAllFiles(Model model) {
        log.info("Получение списка удаленных файлов");
        String url = "http://localhost:8085/api/sdk/files-deleted";
        List<DTOMessage> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        return "filesdeleted";
    }

    /**
     * Очистка списка удаленных файлов
     */
    @GetMapping("/clean")
    public String deleteAll(Model model) {
        String url = "http://localhost:8085/api/sdk/files-clean";
        List<DTOMessage> dtoMessages = restTemplate.getForObject(url, List.class);
        clientMessageService.cleanAllDeletedMessageFromS3(dtoMessages);
        model.addAttribute("listOfFiles", dtoMessages);
        log.info("Список удаленных файлов очищен");
        return "filesdeleted";
    }

    /**
     * Удаление файла на совсем
     */
    @PostMapping("/full")
    public String fullDelete(HttpServletRequest request, Model model) {
        if (request.getParameter("id").equals("")) {
            log.error("Не введено id для удаления файла");
            model.addAttribute("error", "Введите id для удаления файла");
            return "error-page";
        }
        Long id = Long.parseLong(request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/full-delete";
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage != null && dtoMessage.getFileNameForS3() != null) {
            clientMessageService.deleteFromS3ByName(dtoMessage.getFileNameForS3());
            log.info("Файл {} удален", dtoMessage.getOriginFileName());
        }
        return "redirect:/deleted";
    }

    /**
     * Восстановление файла
     */
    @GetMapping("/restore")
    public String restoreFile(HttpServletRequest request, Model model) {
        if (request.getParameter("id").equals("")) {
            log.error("Не введено id для восстановления файла");
            model.addAttribute("error", "Введите id для удаления файла");
            return "error-page";
        }
        Long id = Long.parseLong(request.getParameter("id"));
        var url = "http://localhost:8085/api/sdk/restore-file";
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage != null && dtoMessage.getFileNameForS3() != null) {
            log.info("Файл {} восстановлен", dtoMessage.getOriginFileName());
        } else {
            log.error("Данные о файле с Id={} в БД отсутствуют", id);
            model.addAttribute("error", "Данные о файле с Id =" + id + " в БД отсутствуют");
            return "error-page";
        }
        return "redirect:/deleted";
    }
}
