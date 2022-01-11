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

@Service
@RequiredArgsConstructor
public class ClientDownloadHistoryService {

    private final static Logger log = LogManager.getLogger(ClientDownloadHistoryService.class);
    private final RestTemplate restTemplate;
    private final ClientDTOMessageService clientDTOMessageService;
    private final AuthorizationHeaderService authorizationHeaderService;

    public List<DownloadHistoryDto> getAllDownloadHistory(Long id) {
        if (id == 0) {
            log.error("Не введено id файла для получения истории загрузки");
            throw new NoIdException("Введите id для получения истории загрузки файла");
        }
        log.info("Получение истории скачивания файла по id={}", id);
        HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForPostRequest(id);
        try {
            DownloadHistoryDto[] historyDtoList =
                    restTemplate.postForObject(clientDTOMessageService.getUrl("download-history"), request,
                            DownloadHistoryDto[].class);
            if (historyDtoList != null) {
                return Arrays.asList(historyDtoList);
            } else {
                return Collections.emptyList();
            }
        } catch (HttpClientErrorException e) {
            throw new AuthorizationJwtTokenException("Ошибка валидации токена: ");
        }
    }
}
