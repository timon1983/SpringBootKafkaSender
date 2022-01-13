package com.example.sbks.service.impl;

import com.example.sbks.dto.UserSecurity;
import com.example.sbks.model.UserAuth;
import com.example.sbks.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения объекта UserDetails
 */
@Service("userDetailService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    /**
     * Получение объекта UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("UserAuth does not exists"));
        return UserSecurity.fromUserAuth(userAuth);
    }
}