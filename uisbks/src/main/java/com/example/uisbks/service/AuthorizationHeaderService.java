package com.example.uisbks.service;

import com.example.uisbks.dtomodel.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Сервис для формирования объекта запроса вместе с авторизационными данными
 */
@Service
@RequiredArgsConstructor
public class AuthorizationHeaderService {

    private final Token token;

    /**
     * Получение HttpEntity<Object> с токеном в header
     */
    public HttpEntity<Object> getHttpEntityForRequest(Object o) {
        return new HttpEntity<>(o, getHeader(token));
    }

    /**
     * Запись токена в header
     */
    private MultiValueMap<String, String> getHeader(Token token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", token.getToken());
        return headers;
    }
}
