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
    @Value("${public-S3-reference}")
    private String publicS3Reference;

    /**
     * Метод для выполнения операций по сохранению файла
     */
    public void doOperationToSaveFiles(DTOMessage dtoMessage) throws URISyntaxException, IOException {
        URI uri = new URI(clientDTOMessageService.getUrl("create"));
        log.info("Отправка данных по файлу {} в БД", dtoMessage.getOriginFileName());
        setCache(dtoMessage);
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
    public void doOperationToDeleteFiles(String urlEndPoint, Long id) {
        if (id == 0) {
            throw new NoIdException("Введите id для удаления файла");
        }
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint), id, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        }
        log.info("Файл c id={} удален", id);
    }

    /**
     * Метод для выполнения операций по отправлению файла
     */
    public void doOperationToSendFiles(String urlEndPoint, String name) {
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint), name, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && !dtoInfoModelClient.getIsError()) {
            log.info("Файл {} отправлен в kafka", name);
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
        log.info("Запись файла {} в кэш", dtoMessage.getFileNameForS3());
        File file = new File(String.format("uisbks/files/%s", dtoMessage.getFileNameForS3()));
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(dtoMessage.getContent());
        } catch (IOException e) {
            log.error("Ошибка при записи файла {} кэш", dtoMessage.getFileNameForS3());
        }
    }
}
