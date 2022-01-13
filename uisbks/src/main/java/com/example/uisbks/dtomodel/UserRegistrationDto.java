package com.example.uisbks.dtomodel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserRegistrationDto {
    private Long id;
    private String name;
    private String email;
    private String password;
}
