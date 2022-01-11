package com.example.uisbks.service;

import com.example.uisbks.dtomodel.AuthDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ClientAuthServiceTest {

    @Mock
    ClientAuthService clientAuthService;

    @Test
    void check_sendAuth() {
        AuthDto authDto = AuthDto.builder()
                .email("xxx@mail.ru")
                .password("xxx")
                .build();
        clientAuthService.sendAuth(authDto);
        verify(clientAuthService).sendAuth(authDto);
    }

    @Test
    void check_sendOut() {
        clientAuthService.sendOut();
        verify(clientAuthService).sendOut();
    }
}