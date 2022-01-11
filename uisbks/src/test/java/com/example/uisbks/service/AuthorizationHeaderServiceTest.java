package com.example.uisbks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthorizationHeaderServiceTest {

    @Mock
    AuthorizationHeaderService authorizationHeaderService;

    @Test
    void check_getHttpEntityForPostRequest_Should_Return_HttpEntity() {
        Object o = new Object();
        HttpEntity<Object> httpEntity = new HttpEntity<>(o);
        when(authorizationHeaderService.getHttpEntityForRequest(o)).thenReturn(httpEntity);
    }

    @Test
    void check_getHttpEntityForGetRequest_Should_Return_HttpEntity() {
        when(authorizationHeaderService.getHttpEntityForRequest(null)).thenReturn(new HttpEntity<>(null));
    }
}