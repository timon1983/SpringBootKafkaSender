package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DownloadHistoryDto;
import com.example.uisbks.exception.AuthorizationJwtTokenException;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с историей загрузок файлов
 */
@Service
@RequiredArgsConstructor
public class ClientDownloadHistoryService {

    private final static Logger log = LogManager.getLogger(ClientDownloadHistoryService.class);
    private final RestTemplate restTemplate;
    private final ClientDTOMessageService clientDTOMessageService;
    private final AuthorizationHeaderService authorizationHeaderService;

    /**
     * Получение истории загрузки файла по id
     */
    public List<DownloadHistoryDto> getAllDownloadHistory(Long id) {
        if (id == 0) {
            log.error("Не введено id файла для получения истории загрузки");
            throw new NoIdException("Введите id для получения истории загрузки файла");
        }
        log.info("Получение истории скачивания файла по id={}", id);
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForRequest(id);
        try {
            return Optional.ofNullable(restTemplate
                            .postForObject(clientDTOMessageService
                                            .getUrl("sbk/download-history"), request,
                                    DownloadHistoryDto[].class))
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }
}
