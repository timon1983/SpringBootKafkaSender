package com.example.uisbks.service;

import com.example.uisbks.dtomodel.InfoModelClientDto;
import com.example.uisbks.dtomodel.MessageDto;
import com.example.uisbks.exception.AuthorizationJwtTokenException;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
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
    private final AuthorizationHeaderService authorizationHeaderService;

    /**
     * Метод для полного удаления файла из корзины
     */
    public void fullDeleteOfFile(Long id) {
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(id);
        try {
            InfoModelClientDto infoModelClientDto = restTemplate.postForObject(clientDTOMessageService
                    .getUrl("full-delete"), request, InfoModelClientDto.class);
            checkDtoInfoModelClient(infoModelClientDto);
            log.info("Файл c id={} полностью удален", id);
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }

    /**
     * Метод для восстановления файла из корзины
     */
    public void restoreFile(Long id) {
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(id);
        try {
            InfoModelClientDto infoModelClientDto = restTemplate.postForObject(clientDTOMessageService
                    .getUrl("restore-file"), request, InfoModelClientDto.class);
            checkDtoInfoModelClient(infoModelClientDto);
            log.info("Файл c id={} восстановлен", id);
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }


    /**
     * Метод для получение списка файлов в корзине
     */
    public List<LinkedHashMap<String, Object>> getListOfDeletedFile() {
        HttpEntity<MultiValueMap<String, String>> request = authorizationHeaderService.getHttpEntityForGetRequest();
        try {
            ResponseEntity<List<LinkedHashMap<String, Object>>> response =
                    restTemplate.exchange(clientDTOMessageService.getUrl("files-deleted"), HttpMethod.GET,
                            request, new ParameterizedTypeReference <List<LinkedHashMap<String, Object>>>() {});
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }

    /**
     * Метод для очистки корзины
     */
    public List<String> cleanListOfDeletedFile() {
        HttpEntity<MultiValueMap<String, String>> request = authorizationHeaderService.getHttpEntityForGetRequest();
        try {
            ResponseEntity<List<String>> response = restTemplate.exchange(clientDTOMessageService.getUrl("files-clean"),
                    HttpMethod.GET, request, new ParameterizedTypeReference<List<String>>() {});
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
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }

    /**
     * Метод проверки DTOInfoModelClient на null
     */
    private void checkDtoInfoModelClient(InfoModelClientDto infoModelClientDto) {
        if (infoModelClientDto != null) {
            if (infoModelClientDto.getIsError()) {
                log.error(infoModelClientDto.getInfo());
                throw new NoIdException(infoModelClientDto.getInfo());
            } else {
                try {
                    Files.deleteIfExists(Paths.get(String.format("uisbks/files/%s", infoModelClientDto.getInfo())));
                } catch (IOException e) {
                    throw new NoIdException("ошибка при удалении из кеша");
                }
            }
        }
    }
}
