package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.exception.NoIdException;
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
     * Метод для получения списка всех загруженных файлов
     */
    public String getListOfFiles(Model model, Logger log){
        log.info("Получение списка загруженных файлов");
        var url = "http://localhost:8085/api/sdk/files";
        List<DTOMessage> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        return "filesj";
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
    public String doOperationToDeleteFiles(String urlEndPoint, HttpServletRequest request, Logger log) {
        Long id = Long.parseLong(request.getParameter("id"));
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        DTOMessage dtoMessage = restTemplate.postForObject(url, id, DTOMessage.class);
        if (dtoMessage == null) {
            throw new NoIdException(String.format("Данные о файле с id=%d в БД отсутствуют", id));
        }
        log.info("Файл {} удален", dtoMessage.getOriginFileName());
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
