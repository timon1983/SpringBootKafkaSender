package com.example.sbks.dto;

import com.example.sbks.model.Role;
import com.example.sbks.model.StatusAuth;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserAuthDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private final Role role = Role.USER;
    private final StatusAuth statusAuth = StatusAuth.ACTIVE;
}
