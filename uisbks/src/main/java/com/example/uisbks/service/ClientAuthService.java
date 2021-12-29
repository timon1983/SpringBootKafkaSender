package com.example.uisbks.service;

import com.example.uisbks.configuration.GlobalConfig;
import com.example.uisbks.dtomodel.AuthDto;
import com.example.uisbks.dtomodel.DTOInfoModelClient;
import com.example.uisbks.exception.ErrorLoginException;
import com.example.uisbks.exception.SuccessLoginException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ClientAuthService {

    private final static Logger log = LogManager.getLogger(ClientAuthService.class);
    private final RestTemplate restTemplate;
    private final GlobalConfig config;

    public void sendAuth(AuthDto authDto) {
        DTOInfoModelClient dtoInfoModelClient =
                restTemplate.postForObject("api/sdk/auth", authDto, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null) {
            if (!dtoInfoModelClient.getIsError()) {
                config.setToken(dtoInfoModelClient.getInfo());
                log.info("Аутентификация прошла успешно");
                throw new SuccessLoginException(dtoInfoModelClient.getInfo());
            } else {
                log.info("Ошибка аутентификации");
                throw new ErrorLoginException(dtoInfoModelClient.getInfo());
            }
        }
    }
}
