package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.dtomodel.DTORequestMessage;
import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.service.ClientDTOMessageService;
import com.example.uisbks.service.ClientMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Контроллер для работы с сообщениями от клиента
 */
@Controller
@RequestMapping("/create")
@RequiredArgsConstructor
public class ClientMessageController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final ClientMessageService clientMessageService;
    private final ClientDTOMessageService clientDTOMessageService;

    /**
     * Переход на страницу добавления файла
     */
    @GetMapping
    public String getCreatePage() {
        return JspPage.FILE_INSERT;
    }

    /**
     * Загрузка файла с UI
     */
    @PostMapping
    public String createMessage(@ModelAttribute DTORequestMessage dtoRequestMessage)
            throws URISyntaxException, IOException {
        log.info("Получение сообщения от клиента");
        DTOMessage dtoMessage = clientDTOMessageService.getDTOMessage(dtoRequestMessage);
        clientMessageService.doOperationToSaveFiles(dtoMessage);
        return JspPage.FILE_INSERT;
    }

    /**
     * Получение списка всех загруженных файлов
     */
    @GetMapping("/files")
    public String getAllFiles(Model model) {
        List<LinkedHashMap<String, Object>> dtoMessages = clientMessageService.getListOfFiles();
        model.addAttribute("listOfFiles", dtoMessages);
        return JspPage.FILE_LIST;
    }

    /**
     * Удаление файла по id
     */
    @GetMapping("/file-delete/{id}")
    public String deleteFileById(@PathVariable Long id) {
        clientMessageService. doOperationToDeleteFiles(id);
        return "redirect:/create/files";
    }

    /**
     * Получение файла по id
     */
    @GetMapping("/open-file-id/{ip}")
    public String openFileById(@RequestParam Long id, @PathVariable String ip) {
        DTODownloadHistory downloadHistory = clientDTOMessageService.getDTODownloadHistoryById(id, ip);
        String url = clientMessageService.doOperationWithFilesForOpenByIdOrByName("open-id", downloadHistory);
        return String.join("",
                "redirect:",
                url);

    }

    /**
     * Получение файла по имени
     */
    @GetMapping("/open-file-name/{ip}")
    public String openFileByName(@RequestParam String name, @PathVariable String ip) {
        DTODownloadHistory downloadHistory = clientDTOMessageService.getDTODownloadHistoryByName(name, ip);
        String url = clientMessageService.doOperationWithFilesForOpenByIdOrByName("open-name", downloadHistory);
        return String.join("",
                "redirect:",
                url);
    }

    /**
     * Отправка сообщения в SBKC
     */
    @PostMapping("/send")
    public String sendFile(@RequestBody String name) {
        clientMessageService.doOperationToSendFiles(name);
        return "redirect:/create/files";
    }
}
