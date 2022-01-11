package com.example.uisbks.dtomodel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthDto {

    private String email;
    private String password;
}
