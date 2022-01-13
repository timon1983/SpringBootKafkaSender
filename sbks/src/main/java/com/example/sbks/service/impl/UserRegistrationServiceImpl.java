package com.example.sbks.service.impl;

import com.example.sbks.dto.UserAuthDto;
import com.example.sbks.exception.DuplicateEmailException;
import com.example.sbks.mapper.MapperForModel;
import com.example.sbks.model.UserAuth;
import com.example.sbks.repository.UserAuthRepository;
import com.example.sbks.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final MapperForModel mapper = Mappers.getMapper(MapperForModel.class);
    private final PasswordEncoder passwordEncoder;
    private final UserAuthRepository userAuthRepository;

    @Override
    public void registrationUser(UserAuthDto userAuthDto) {
        userAuthDto.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        UserAuth userAuth = mapper.userAuthDtoToUserAuth(userAuthDto);
        userAuthRepository.findByEmail(userAuth.getEmail())
                .ifPresent(it -> {
                            throw new DuplicateEmailException("Пользователь с таким email существует");
                        }
                );
        userAuthRepository.save(userAuth);
    }
}
