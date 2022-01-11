package com.example.sbks.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthenticationDTO {
    private String email;
    private String password;
}
