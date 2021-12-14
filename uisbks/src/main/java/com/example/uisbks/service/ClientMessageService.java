package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageService {

    private final RestTemplate restTemplate;

    /**
     * Метод для выполнения операций с файлами в корзине(полное удаление, восстановление)
     */
    public String doOperationWithDeletedFile(String urlEndPoint, String action, HttpServletRequest request,
                                             Model model, Logger log) {
        if (request.getParameter("id").isBlank()) {
            return checkingForId(model, log);
        }
        Long id = Long.parseLong(request.getParameter("id"));
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage != null && dtoMessage.getFileNameForS3() != null) {
            log.info("Файл {} {}", dtoMessage.getOriginFileName(), action);
        } else {
            return getErrorPage(log, model);
        }
        return "redirect:/deleted";
    }

    /**
     * Метод для выполнения оперций над списком файлов в корзине(получение списка, очистка корзины)
     */
    public void doOperationWithListOfDeletedFile(Model model, String urlEndPoint) {
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        List<DTOMessage> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
    }

    /**
     * Метод для проверки поля ввода ID на "null"
     */
    public String checkingForId(Model model, Logger log) {
        log.error("Не введено id для восстановления файла");
        model.addAttribute("error", "Введите id для удаления файла");
        return "error-page";
    }

    /**
     * Метод для выполнения операций скачивания файла по id или имени
     */
    public String doOperationWithFilesForOpenByIdOrName(String urlEndPoint, DTODownloadHistory downloadHistory,
                                                        Model model, Logger log) {

        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        DTOMessage dtoMessage = restTemplate.postForObject(url, downloadHistory, DTOMessage.class);
        if (dtoMessage != null) {
            log.info("Файл получен: [id: {}, name: {}]", dtoMessage.getId(), dtoMessage.getOriginFileName());
            return String.format("redirect:https://d2lzjz6kkt1za6.cloudfront.net/%s", dtoMessage.getFileNameForS3());
        } else {
            return getErrorPage(log, model);
        }
    }

    /**
     * Метод для выполнения операций по удалению файла
     */
    public String doOperationToDeleteFiles(String urlEndPoint, HttpServletRequest request,
                                           Model model, Logger log) {
        Long id = Long.parseLong(request.getParameter("id"));
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage != null) {
            log.info("Файл {} удален", dtoMessage.getOriginFileName());
        } else {
            return getErrorPage(log, model);
        }
        return "redirect:/create/files";
    }

    /**
     * Метод для выполнения операций по отправлению файла
     */
    public String doOperationToSendFiles(String urlEndPoint, HttpServletRequest request,
                                         Model model, Logger log) {
        var name = request.getParameter("name");
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        DTOMessage dtoMessage = restTemplate.postForObject(url, name, DTOMessage.class);
        if (dtoMessage != null) {
            log.info("Файл {} отправлен в kafka", dtoMessage.getOriginFileName());
            return "redirect:/create/files";
        } else {
            return getErrorPage(log, model);
        }
    }

    /**
     * Метод для выполнения операций по сохранению файла
     */
    public String doOperationToSaveFiles(DTOMessage dtoMessage, Logger log) throws URISyntaxException {
        if (dtoMessage != null) {
            URI uri = new URI("http://localhost:8085/api/sdk/create");
            log.info("Отправка данных по файлу {} в БД", dtoMessage.getOriginFileName());
            restTemplate.postForObject(uri, dtoMessage, DTOMessage.class);
        } else {
            log.error("Нет файла для загрузки");
        }
        return "message-insert-form";
    }

    /**
     * Метод для редиректа на страницу ошибки
     */
    public String getErrorPage(Logger log, Model model) {
        log.error("Данные о файле в БД отсутствуют");
        model.addAttribute("error", "Данные о файле в БД отсутствуют");
        return "error-page";
    }
}
