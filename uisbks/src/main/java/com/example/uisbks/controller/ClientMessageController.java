package com.example.uisbks.controller;

import com.example.uisbks.dtomodel.DownloadHistoryDto;
import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.dtomodel.MessageDto;
import com.example.uisbks.dtomodel.RequestMessageDto;
import com.example.uisbks.service.ClientCacheService;
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
    private final ClientCacheService clientCacheService;

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
    public String createMessage(@ModelAttribute RequestMessageDto requestMessageDto)
            throws URISyntaxException, IOException {
        log.info("Получение сообщения от клиента");
        MessageDto messageDto = clientDTOMessageService.getDTOMessage(requestMessageDto);
        clientMessageService.doOperationToSaveFiles(messageDto);
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
        clientMessageService.doOperationToDeleteFiles(id);
        return "redirect:/create/files";
    }

    /**
     * Получение файла по id
     */
    @GetMapping("/open-file-id/{ip}")
    public String openFileById(@RequestParam Long id, @PathVariable String ip) {
        DownloadHistoryDto downloadHistory = clientDTOMessageService.getDTODownloadHistoryById(id, ip);
        String url = clientMessageService.doOperationWithFilesForOpenById(downloadHistory);
        return String.join("",
                "redirect:",
                url);
    }

    /**
     * Получение файла по имени
     */
    @GetMapping("/open-file-name/{ip}")
    public String openFileByName(@RequestParam String name, @PathVariable String ip) {
        DownloadHistoryDto downloadHistory = clientDTOMessageService.getDTODownloadHistoryByName(name, ip);
        String url = clientMessageService.doOperationWithFilesForOpenByName(downloadHistory);
        return String.join("",
                "redirect:",
                url);
    }

    /**
     * Отправка сообщения в SBKC
     */
    @PostMapping("/send")
    public String sendFile(@RequestBody String request) {
        String name = request.substring(request.indexOf("=") + 1);
        clientMessageService.doOperationToSendFiles(name);
        return "redirect:/create/files";
    }

    /**
     * Очитстка кеша
     */
    @GetMapping("/clean-cache") // todo сделать тест
    public String cleanCache(){
        log.info("Очистка кеша");
        clientCacheService.cleanFileCache();
        return "redirect:/create/files";
    }
}
