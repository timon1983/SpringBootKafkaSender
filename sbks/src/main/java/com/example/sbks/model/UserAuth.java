package com.example.sbks.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "usersauth")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private StatusAuth statusAuth;
}
