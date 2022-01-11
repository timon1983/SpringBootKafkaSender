package com.example.uisbks.service;

import com.example.uisbks.dtomodel.InfoModelClientDto;
import com.example.uisbks.dtomodel.MessageDto;
import com.example.uisbks.dtomodel.Token;
import com.example.uisbks.exception.AuthorizationJwtTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class AuthorizationHeaderService {
    private final Token token;

    public HttpEntity<Object> getHttpEntityForPostRequest(Object o){
        return new HttpEntity<>(o, getHeader(token));
    }

    public HttpEntity<MultiValueMap<String, String>> getHttpEntityForGetRequest(){
        return new HttpEntity<>(null, getHeader(token));
    }

    private MultiValueMap<String, String> getHeader(Token token){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization" , token.getToken());
        return headers;
    }
}
