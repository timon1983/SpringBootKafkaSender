package com.example.sbks.model;

import lombok.Data;

import javax.persistence.*;

// todo в entity лучше не использовать Data
//  https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/#Avoid_Data
@Data
@Entity
@Table(name = "usersauth")
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email") // todo как по мне, эти аннотации излишне, кроме где они действительно нужны
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private StatusAuth statusAuth;
}
