package com.example.uisbks.service;

import com.example.uisbks.dtomodel.InfoModelClientDto;
import com.example.uisbks.dtomodel.UserRegistrationDto;
import com.example.uisbks.exception.PasswordInvalidateException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Сервис для обработки регистрационных данных пользователя
 */
@Service
@RequiredArgsConstructor
public class ClientRegistrationService {

    private final static Logger log = LogManager.getLogger(ClientRegistrationService.class);
    private final RestTemplate restTemplate;
    private final ClientDTOMessageService clientDTOMessageService;

    /**
     * Отправка регистрационных данных пользователя в sbks
     */
    public void registrationNewUser(UserRegistrationDto userRegistrationDto) {
        try {
            URI uri = new URI(clientDTOMessageService.getUrl("auth/registration"));
            InfoModelClientDto infoModelClientDto = restTemplate.postForObject(uri,
                    validateAndGetUserRegistrationDto(userRegistrationDto), InfoModelClientDto.class);
            if (infoModelClientDto != null) {
                log.info(infoModelClientDto.getInfo());
                throw new PasswordInvalidateException(infoModelClientDto.getInfo());
            }
        } catch (URISyntaxException e) {
            log.error("Ошибка при создании URI");
        }
    }

    /**
     * Валидация регистрационных данных пользователя
     */
    private UserRegistrationDto validateAndGetUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
        String[] passwords = userRegistrationDto.getPassword().split(",");
        if (passwords[0].equals(passwords[1])) {
            userRegistrationDto.setPassword(passwords[0]);
            return userRegistrationDto;
        } else {
            throw new PasswordInvalidateException("Пароли не совпадают, введите заново пароль");
        }
    }
}
