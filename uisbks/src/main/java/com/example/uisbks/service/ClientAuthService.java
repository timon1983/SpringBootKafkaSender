package com.example.uisbks.service;

import com.example.uisbks.dtomodel.AuthDto;
import com.example.uisbks.dtomodel.InfoModelClientDto;
import com.example.uisbks.dtomodel.Token;
import com.example.uisbks.exception.ErrorLoginException;
import com.example.uisbks.exception.SuccessLoginException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Сервис для аутентификации пользователя и выхода из аккаунта
 */
@Service
@RequiredArgsConstructor
public class ClientAuthService {

    private final static Logger log = LogManager.getLogger(ClientAuthService.class);
    private final RestTemplate restTemplate;
    private final Token token;
    private final AuthorizationHeaderService authorizationHeaderService;

    /**
     * Отправка данных для аутентификации(email и пароль)
     */
    public void sendAuth(AuthDto authDto) {
        try {
            URI uri = new URI("http://localhost:8086/api/auth/login");
            InfoModelClientDto infoModelClientDto =
                    restTemplate.postForObject(uri, authDto, InfoModelClientDto.class);
            checkInfoModelClientDtoResponse(infoModelClientDto);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выход из аккаунта, удаление токена
     */
    public void sendOut() {
        try {
            HttpEntity<Object> request = authorizationHeaderService.getHttpEntityForRequest(null);
            URI uri = new URI("http://localhost:8086/api/auth/logout");
            token.setToken(null);
            restTemplate.postForObject(uri, request, InfoModelClientDto.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException e) {
            log.info("Выход из аккаунта");
        }
    }

    /**
     * Проверка объекта InfoModelClientDto
     */
    private void checkInfoModelClientDtoResponse(InfoModelClientDto infoModelClientDto) {
        if (infoModelClientDto != null) {
            if (!infoModelClientDto.getIsError()) {
                token.setToken(infoModelClientDto.getInfo());
                log.info("Аутентификация прошла успешно");
                throw new SuccessLoginException(infoModelClientDto.getInfo());
            } else {
                log.error("Ошибка аутентификации");
                throw new ErrorLoginException(infoModelClientDto.getInfo());
            }
        }
    }
}
