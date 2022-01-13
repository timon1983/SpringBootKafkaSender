package com.example.sbks.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_auth")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserAuth {
    @Id
    @SequenceGenerator(name = "userAuthIdSeq", sequenceName = "user_auth_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userAuthIdSeq")
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
