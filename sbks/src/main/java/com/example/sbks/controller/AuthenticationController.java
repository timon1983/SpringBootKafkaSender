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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final static Logger log = LogManager.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final UserAuthRepository userAuthRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<InfoDto> authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(),
                    authenticationDTO.getPassword())));
            UserAuth userAuth = userAuthRepository.findByEmail(authenticationDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Такого UserAuth не существует"));
            String token = jwtTokenProvider.createToken(userAuth.getEmail(), userAuth.getRole().name());
            InfoDto infoDto = InfoDto.builder()
                    .info(token)
                    .build();
            return new ResponseEntity<>(infoDto, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new NoSuchDataFileException("Некоректная комбинация email/password");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
        SecurityContextHolder.clearContext();
        log.info("Отмена аутентификации");
    }
}
