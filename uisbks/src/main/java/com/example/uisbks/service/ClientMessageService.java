package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOInfoModelClient;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.dtomodel.JspPage;
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
    private final ClientDTOMessageService clientDTOMessageService;

    /**
     * Метод для получения списка всех загруженных файлов
     */
    public String getListOfFiles(Model model, Logger log) {
        log.info("Получение списка загруженных файлов");
        List<DTOMessage> dtoMessages =
                restTemplate.getForObject(clientDTOMessageService.getUrl("files"), List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        return JspPage.FILE_LIST;
    }

    /**
     * Метод для выполнения операций скачивания файла по id или по имени
     */
    public String doOperationWithFilesForOpenByIdOrByName(String urlEndPoint, DTODownloadHistory downloadHistory, Logger log) {
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint),
                downloadHistory, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        } else if (dtoInfoModelClient != null && !dtoInfoModelClient.getIsError()) {
            log.info("Файл получен: [id: {}, name: {}]", downloadHistory.getId(), downloadHistory.getFileName());
            return String.format("redirect:https://d2lzjz6kkt1za6.cloudfront.net/%s", dtoInfoModelClient.getInfo());
        }
        return "redirect:/create/files";
    }

    /**
     * Метод для выполнения операций по удалению файла
     */
    public String doOperationToDeleteFiles(String urlEndPoint, HttpServletRequest request, Logger log) {
        Long id = Long.parseLong(request.getParameter("id"));
        DTOMessage dtoMessage =
                restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint), id, DTOMessage.class);
        if (dtoMessage == null) {
            throw new NoIdException(String.format("Данные о файле с id=%d в БД отсутствуют", id));
        }
        log.info("Файл {} удален", dtoMessage.getOriginFileName());
        return "redirect:/create/files";
    }

    /**
     * Метод для выполнения операций по отправлению файла
     */
    public String doOperationToSendFiles(String urlEndPoint, HttpServletRequest request, Logger log) {
        String name = request.getParameter("name");
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint), name, DTOInfoModelClient.class);
        if (dtoInfoModelClient == null) {
            return "redirect:/create/files";
        }

        if (dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        } else {
            log.info("Файл {} отправлен в kafka", name);
            return "redirect:/create/files";
        }
    }

    /**
     * Метод для выполнения операций по сохранению файла
     */
    public String doOperationToSaveFiles(DTOMessage dtoMessage, Logger log) throws URISyntaxException {
        URI uri = new URI(clientDTOMessageService.getUrl("create"));
        log.info("Отправка данных по файлу {} в БД", dtoMessage.getOriginFileName());
        restTemplate.postForObject(uri, dtoMessage, DTOMessage.class);
        return JspPage.FILE_INSERT;
    }
}
