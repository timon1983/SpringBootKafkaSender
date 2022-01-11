package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DownloadHistoryDto;
import com.example.uisbks.dtomodel.InfoModelClientDto;
import com.example.uisbks.dtomodel.MessageDto;
import com.example.uisbks.exception.AuthorizationJwtTokenException;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
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
    private final AuthorizationHeaderService authorizationHeaderService;


    /**
     * Метод для выполнения операций по сохранению файла
     */
    public void doOperationToSaveFiles(MessageDto messageDto) throws URISyntaxException, IOException {
        URI uri = new URI(clientDTOMessageService.getUrl("create"));
        log.info("Отправка данных по файлу {} в БД", messageDto.getOriginFileName());
        clientCacheService.setCache(messageDto, "uisbks/files/");
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(messageDto);
        try {
            InfoModelClientDto infoModelClientDto = restTemplate.postForObject(uri, request, InfoModelClientDto.class);
            if (infoModelClientDto != null && infoModelClientDto.getIsError()) {
                log.error(infoModelClientDto.getInfo());
                throw new NoIdException(infoModelClientDto.getInfo());
            }
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }

    /**
     * Метод для получения списка всех загруженных файлов
     */
    public List<LinkedHashMap<String, Object>> getListOfFiles() {
        HttpEntity<MultiValueMap<String, String>> request = authorizationHeaderService.getHttpEntityForGetRequest();
        log.info("Получение списка загруженных файлов");
        try {
            ResponseEntity<List<LinkedHashMap<String, Object>>> response =
                    restTemplate.exchange(clientDTOMessageService.getUrl("files"), HttpMethod.GET,
                            request, new ParameterizedTypeReference <List<LinkedHashMap<String, Object>>>() {});
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }

    /**
     * Метод для выполнения операций скачивания файла по id
     */
    public String doOperationWithFilesForOpenById(DownloadHistoryDto downloadHistoryDto) {
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(downloadHistoryDto);
        try {
            InfoModelClientDto infoModelClientDto = restTemplate
                    .postForObject(clientDTOMessageService.getUrl("open-id"), request, InfoModelClientDto.class);
            return getUrlForFileAfterCheckDtoInfoModelClient(downloadHistoryDto, infoModelClientDto);
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }

    /**
     * Метод для выполнения операций скачивания файла по имени
     */
    public String doOperationWithFilesForOpenByName(DownloadHistoryDto downloadHistoryDto) {
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(downloadHistoryDto);
        try {
            InfoModelClientDto infoModelClientDto = restTemplate
                    .postForObject(clientDTOMessageService.getUrl("open-name"), request, InfoModelClientDto.class);
            return getUrlForFileAfterCheckDtoInfoModelClient(downloadHistoryDto, infoModelClientDto);
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }

    /**
     * Метод проверки DTOInfoModelClient на наличие ошибки и проверка наличие файла в кеше
     */
    private String getUrlForFileAfterCheckDtoInfoModelClient(DownloadHistoryDto downloadHistoryDto,
                                                             InfoModelClientDto infoModelClientDto) {
        if (infoModelClientDto != null) {
            if (infoModelClientDto.getIsError()) {
                log.error(infoModelClientDto.getInfo());
                throw new NoIdException(infoModelClientDto.getInfo());
            } else if (clientCacheService.isCached(infoModelClientDto.getInfo(), "uisbks/files/")) {
                log.info("Файл {} получен из кеша", infoModelClientDto.getInfo());
                throw new NoIdException(String.format("Файл доступен в локальном хранилище по пути D:" +
                        "\\Projects\\SpringBootKafkaSender\\uisbks\\files\\%s", infoModelClientDto.getInfo()));
            } else {
                log.info("Файл получен: [id: {}, name: {}]", downloadHistoryDto.getId(),
                        downloadHistoryDto.getFileName());
                return String.join("",
                        publicS3Reference,
                        "/",
                        infoModelClientDto.getInfo());
            }
        }
        return "/create/files";
    }

    /**
     * Метод для выполнения операций по удалению файла
     */
    public void doOperationToDeleteFiles(Long id) {
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(id);
        try {
            InfoModelClientDto infoModelClientDto = restTemplate
                    .postForObject(clientDTOMessageService.getUrl("delete"), request, InfoModelClientDto.class);
            if (infoModelClientDto != null && infoModelClientDto.getIsError()) {
                log.error(infoModelClientDto.getInfo());
                throw new NoIdException(infoModelClientDto.getInfo());
            }
            log.info("Файл c id={} удален", id);
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
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
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(name);
        try {
            InfoModelClientDto infoModelClientDto = restTemplate
                    .postForObject(clientDTOMessageService.getUrl("send-file"), request, InfoModelClientDto.class);
            if (infoModelClientDto != null && infoModelClientDto.getIsError()) {
                log.error("Ошибка при отправке: [{}]", infoModelClientDto.getInfo());
                throw new NoIdException(infoModelClientDto.getInfo());
            }
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }
}
