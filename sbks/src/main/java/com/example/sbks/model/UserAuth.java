package com.example.sbks.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userauth")
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_auth")
    private StatusAuth statusAuth;
}
