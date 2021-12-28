package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTODownloadHistory;
import com.example.uisbks.dtomodel.DTOInfoModelClient;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageService {

    private final static Logger log = LogManager.getLogger(ClientMessageService.class);
    private final RestTemplate restTemplate;
    private final ClientDTOMessageService clientDTOMessageService;
    private final ClientCacheService clientCacheService;
    @Value("${public-S3-reference}")
    private String publicS3Reference;

    /**
     * Метод для выполнения операций по сохранению файла
     */
    public void doOperationToSaveFiles(DTOMessage dtoMessage) throws URISyntaxException, IOException {
        URI uri = new URI(clientDTOMessageService.getUrl("create"));
        log.info("Отправка данных по файлу {} в БД", dtoMessage.getOriginFileName());
        clientCacheService.setCache(dtoMessage, "uisbks/files/");
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(uri, dtoMessage, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        }
    }

    /**
     * Метод для получения списка всех загруженных файлов
     */
    public List<LinkedHashMap<String, Object>> getListOfFiles() {
        log.info("Получение списка загруженных файлов");
        return restTemplate.getForObject(clientDTOMessageService.getUrl("files"), List.class);
    }

    /**
     * Метод для выполнения операций скачивания файла по id
     */
    public String doOperationWithFilesForOpenById(DTODownloadHistory dtoDownloadHistory) {
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(clientDTOMessageService.getUrl("open-id"),
                dtoDownloadHistory, DTOInfoModelClient.class);
        return getUrlForFileAfterCheckDtoInfoModelClient(dtoDownloadHistory, dtoInfoModelClient);
    }

    /**
     * Метод для выполнения операций скачивания файла по имени
     */
    public String doOperationWithFilesForOpenByName(DTODownloadHistory dtoDownloadHistory) {
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(clientDTOMessageService.getUrl("open-name"),
                dtoDownloadHistory, DTOInfoModelClient.class);
        return getUrlForFileAfterCheckDtoInfoModelClient(dtoDownloadHistory, dtoInfoModelClient);
    }

    /**
     * Метод проверки DTOInfoModelClient на наличие ошибки и проверка наличие файла в кеше
     */
    private String getUrlForFileAfterCheckDtoInfoModelClient(DTODownloadHistory dtoDownloadHistory,
                                                             DTOInfoModelClient dtoInfoModelClient) {
        if (dtoInfoModelClient != null) {
            if (dtoInfoModelClient.getIsError()) {
                log.error(dtoInfoModelClient.getInfo());
                throw new NoIdException(dtoInfoModelClient.getInfo());
            } else if (clientCacheService.isCached(dtoInfoModelClient.getInfo(), "uisbks/files/")) {
                log.info("Файл {} получен из кеша", dtoInfoModelClient.getInfo());
                throw new NoIdException(String.format("Файл доступен в локальном хранилище по пути D:" +
                        "\\Projects\\SpringBootKafkaSender\\uisbks\\files\\%s", dtoInfoModelClient.getInfo()));
            } else {
                log.info("Файл получен: [id: {}, name: {}]", dtoDownloadHistory.getId(),
                        dtoDownloadHistory.getFileName());
                return String.join("",
                        publicS3Reference,
                        "/",
                        dtoInfoModelClient.getInfo());
            }
        }
        return "/create/files";
    }

    /**
     * Метод для выполнения операций по удалению файла
     */
    public void doOperationToDeleteFiles(Long id) {
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject(clientDTOMessageService.getUrl("delete"), id, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        }
        log.info("Файл c id={} удален", id);
    }

    /**
     * Метод для выполнения операций по отправлению файла
     */
    public void doOperationToSendFiles(String name) {
        if (name.isBlank()) {
            throw new NoIdException("Введите имя для отправки файла");
        }
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        log.info("Отправка файла {} в kafka", name);
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject(clientDTOMessageService.getUrl("send-file"), name, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error("Ошибка при отправке: [{}]", dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        }
    }
}
