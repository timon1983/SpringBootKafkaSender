package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOInfoModelClient;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageDeletedService {

    private final static Logger log = LogManager.getLogger(ClientMessageDeletedService.class);
    private final RestTemplate restTemplate;
    private final ClientDTOMessageService clientDTOMessageService;

    /**
     * Метод для выполнения операций с файлами в корзине(полное удаление, восстановление)
     */
    public void doOperationWithDeletedFile(String urlEndPoint, String action, Long id) throws IOException {
        if (id == 0) {
            throw new NoIdException("Введите id для полного удаления файла");
        }
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint),
                id, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        }
        if (dtoInfoModelClient != null && !dtoInfoModelClient.getIsError()) {
            Files.deleteIfExists(Paths.get(String.format("uisbks/files/%s", dtoInfoModelClient.getInfo())));
        }
        log.info("Файл c id={} {}", id, action);
    }

    /**
     * Метод для выполнения оперций над списком файлов в корзине(получение списка, очистка корзины)
     */
    public List<LinkedHashMap<String, Object>> doOperationWithListOfDeletedFile(String urlEndPoint) {
        List<LinkedHashMap<String, Object>> dtoMessages =
                restTemplate.getForObject(clientDTOMessageService.getUrl(urlEndPoint), List.class);
        if (dtoMessages != null && urlEndPoint.equals("files-clean")) {
            dtoMessages.forEach((dtoMessage) -> {
                try {
                    Files.deleteIfExists(Paths.get(String.format("uisbks/files/%s", dtoMessage.get("fileNameForS3"))));
                } catch (IOException e) {
                    throw new NoIdException("ошибка при очистки кеша");
                }
            });
            return new ArrayList<>();
        } else {
            return dtoMessages;
        }
    }
}
