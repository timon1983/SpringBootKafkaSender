package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOInfoModelClient;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageDeletedService {

    private final static Logger log = LogManager.getLogger(ClientMessageDeletedService.class);
    private final RestTemplate restTemplate;
    private final ClientDTOMessageService clientDTOMessageService;
    private final AuthorizationHeaderService authorizationHeaderService;

    /**
     * Метод для полного удаления файла из корзины
     */
    public void fullDeleteOfFile(Long id) {
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(id);
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(clientDTOMessageService
                        .getUrl("full-delete"), request, DTOInfoModelClient.class);
        checkDtoInfoModelClient(dtoInfoModelClient);
        log.info("Файл c id={} полностью удален", id);
    }

    /**
     * Метод для восстановления файла из корзины
     */
    public void restoreFile(Long id) {
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(id);
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(clientDTOMessageService
                        .getUrl("restore-file"), request, DTOInfoModelClient.class);
        checkDtoInfoModelClient(dtoInfoModelClient);
        log.info("Файл c id={} восстановлен", id);
    }


    /**
     * Метод для получение списка файлов в корзине
     */
    public List getListOfDeletedFile() {
        HttpEntity<MultiValueMap<String, String>> request = authorizationHeaderService.getHttpEntityForGetRequest();
        ResponseEntity<List> response = restTemplate.exchange(clientDTOMessageService.getUrl("files-deleted"),
                HttpMethod.GET, request, List.class);
        return response.getBody();
    }

    /**
     * Метод для очистки корзины
     */
    public List<String> cleanListOfDeletedFile() {
        HttpEntity<MultiValueMap<String, String>> request = authorizationHeaderService.getHttpEntityForGetRequest();
        ResponseEntity<List> response = restTemplate.exchange(clientDTOMessageService.getUrl("files-clean"),
                HttpMethod.GET, request, List.class);
        List<String> fileNames = response.getBody();
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
    private void checkDtoInfoModelClient(DTOInfoModelClient dtoInfoModelClient) {
        if (dtoInfoModelClient != null) {
            if (dtoInfoModelClient.getIsError()) {
                log.error(dtoInfoModelClient.getInfo());
                throw new NoIdException(dtoInfoModelClient.getInfo());
            } else {
                try {
                    Files.deleteIfExists(Paths.get(String.format("uisbks/files/%s", dtoInfoModelClient.getInfo())));
                } catch (IOException e) {
                    throw new NoIdException("ошибка при удалении из кеша");
                }
            }
        }
    }
}
