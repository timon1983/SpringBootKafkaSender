package com.example.sbks.controller;

import com.example.sbks.dto.AuthenticationDTO;
import com.example.sbks.dto.InfoDto;
import com.example.sbks.exception.NoSuchDataFileException;
import com.example.sbks.model.UserAuth;
import com.example.sbks.repository.UserAuthRepository;
import com.example.sbks.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Контроллер для аутентификации и выхода из аккаунта
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final static Logger log = LogManager.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final UserAuthRepository userAuthRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Получение данных с usbks для аутентификации
     */
    @PostMapping("/login")
    public ResponseEntity<InfoDto> authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            String email = authenticationDTO.getEmail();
            authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(email,
                    authenticationDTO.getPassword())));
            UserAuth userAuth = userAuthRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Не найден пользователь с емаил = " + email)); // чтобы исключениее было информативнее
            String token = jwtTokenProvider.createToken(userAuth.getEmail(), userAuth.getRole().name());
            InfoDto infoDto = InfoDto.builder()
                    .info(token)
                    .build();
            // todo в остальных местах сделать по аналогии
            return ResponseEntity.ok(infoDto);
        } catch (AuthenticationException e) {
            throw new NoSuchDataFileException("Некорректная комбинация email/password");
        }
    }

    /**
     * Отмена аутентификации
     */
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
        SecurityContextHolder.clearContext();
        log.info("Отмена аутентификации");
    }
}
