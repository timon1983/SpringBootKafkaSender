package com.example.sbks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    UserDetailsServiceImpl userDetailsService;
    @Mock
    UserDetails userDetails;

    @Test
    void check_loadUserByUsername() {
        lenient().when(userDetailsService.loadUserByUsername("admin@mail.com")).thenReturn(userDetails);
    }
}