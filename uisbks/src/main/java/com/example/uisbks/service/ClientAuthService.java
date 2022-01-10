package com.example.uisbks.service;

import com.example.uisbks.dtomodel.AuthDto;
import com.example.uisbks.dtomodel.InfoModelClientDto;
import com.example.uisbks.dtomodel.Token;
import com.example.uisbks.exception.ErrorLoginException;
import com.example.uisbks.exception.SuccessLoginException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class ClientAuthService {

    private final static Logger log = LogManager.getLogger(ClientAuthService.class);
    private final RestTemplate restTemplate;
    private final Token token;

    public void sendAuth(AuthDto authDto) {
        URI uri = null;
        try {
            uri = new URI("http://localhost:8086/api/auth/login");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        InfoModelClientDto infoModelClientDto =
                restTemplate.postForObject(uri, authDto, InfoModelClientDto.class);
        if (infoModelClientDto != null) {
            if (!infoModelClientDto.getIsError()) {
                token.setToken(infoModelClientDto.getInfo());
                log.info("Аутентификация прошла успешно");
                throw new SuccessLoginException(infoModelClientDto.getInfo());
            } else {
                log.info("Ошибка аутентификации");
                throw new ErrorLoginException(infoModelClientDto.getInfo());
            }
        }
    }
}
