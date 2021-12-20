package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOInfoModelClient;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.dtomodel.JspPage;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageService {

    private final static Logger log = LogManager.getLogger(ClientMessageService.class);
    private final RestTemplate restTemplate;
    private final ClientDTOMessageService clientDTOMessageService;

    /**
     * Метод для выполнения операций по сохранению файла
     */
    public String doOperationToSaveFiles(DTOMessage dtoMessage) throws URISyntaxException, IOException {
        URI uri = new URI(clientDTOMessageService.getUrl("create"));
        log.info("Отправка данных по файлу {} в БД", dtoMessage.getOriginFileName());
        setCache(dtoMessage);
        restTemplate.postForObject(uri, dtoMessage, DTOMessage.class);
        return JspPage.FILE_INSERT;
    }

    /**
     * Метод для получения списка всех загруженных файлов
     */
    public String getListOfFiles(Model model) {
        log.info("Получение списка загруженных файлов");
        List<LinkedHashMap<String, Object>> dtoMessages =
                restTemplate.getForObject(clientDTOMessageService.getUrl("files"), List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        return JspPage.FILE_LIST;
    }

    /**
     * Метод для выполнения операций скачивания файла по id или по имени
     */
    public String doOperationWithFilesForOpenByIdOrByName(String urlEndPoint, DTODownloadHistory dtoDownloadHistory) {
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint),
                dtoDownloadHistory, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        } else if (dtoInfoModelClient != null && !dtoInfoModelClient.getIsError()) {
            log.info("Файл получен: [id: {}, name: {}]", dtoDownloadHistory.getId(), dtoDownloadHistory.getFileName());
            if (isCached(dtoInfoModelClient.getInfo())) {
                log.info("Файл {} получен из кеша", dtoInfoModelClient.getInfo());
                throw new NoIdException(String.format("Файл доступен в локальном хранилище по пути D:" +
                        "\\Projects\\SpringBootKafkaSender\\uisbks\\files\\%s", dtoInfoModelClient.getInfo()));
            } else {
                return String.format("redirect:https://d2lzjz6kkt1za6.cloudfront.net/%s", dtoInfoModelClient.getInfo());
            }
        }
        return "redirect:/create/files";
    }

    /**
     * Метод для выполнения операций по удалению файла
     */
    public String doOperationToDeleteFiles(String urlEndPoint, HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint), id, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        }
        log.info("Файл c id={} удален", id);
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
        if (dtoInfoModelClient != null && !dtoInfoModelClient.getIsError()) {
            log.info("Файл {} отправлен в kafka", name);
            return "redirect:/create/files";
        }
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        } else {
            throw new NoIdException("Ошибка при отправке");
        }
    }

    /**
     * Проверка наличия файла кеше
     */
    private boolean isCached(String fileName) {
        File[] files = new File("uisbks/files/").listFiles();
        if (files != null) {
            return Arrays.stream(files)
                    .anyMatch(file -> file.getName().equals(fileName));
        }
        return false;
    }

    /**
     * Запись файла в кеш
     */
    private void setCache(DTOMessage dtoMessage) throws IOException {
        Files.createDirectories(Paths.get("uisbks/files/"));
        File file = new File(String.format("uisbks/files/%s", dtoMessage.getFileNameForS3()));
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(dtoMessage.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
