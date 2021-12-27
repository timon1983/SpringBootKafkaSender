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
import java.util.Collections;
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
    public void fullDeleteOfFile(Long id) throws IOException {
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject(clientDTOMessageService.getUrl("full-delete"),
                        id, DTOInfoModelClient.class);
        checkDtoInfoModelClient(dtoInfoModelClient);
        log.info("Файл c id={} полностью удален", id);
    }


    public void restoreFile(Long id) throws IOException {
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject(clientDTOMessageService.getUrl("restore-file"),
                        id, DTOInfoModelClient.class);
        checkDtoInfoModelClient(dtoInfoModelClient);
        log.info("Файл c id={} восстановлен", id);
    }


    /**
     * Метод для получение списка файлов в корзине
     */
    public List<LinkedHashMap<String, Object>> getListOfDeletedFile() {
        return restTemplate.getForObject(clientDTOMessageService.getUrl("files-deleted"), List.class);
    }

    /**
     * Метод для очистки корзины
     */
    public List<String> cleanListOfDeletedFile() {
        List<String> fileNames =
                restTemplate.getForObject(clientDTOMessageService.getUrl("files-clean"), List.class);
        if (fileNames != null) {
            fileNames.forEach((name) -> {
                try {
                    Files.deleteIfExists(Paths.get(String.format("uisbks/files/%s", name)));
                } catch (IOException e) {
                    throw new NoIdException("ошибка при очистки кеша");
                }
            });
        }
        return Collections.emptyList();
    }

    /**
     * Метод проверки DTOInfoModelClient на null
     */
    private void checkDtoInfoModelClient(DTOInfoModelClient dtoInfoModelClient) throws IOException {
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        }
        if (dtoInfoModelClient != null && !dtoInfoModelClient.getIsError()) {
            Files.deleteIfExists(Paths.get(String.format("uisbks/files/%s", dtoInfoModelClient.getInfo())));
        }
    }
}
