package com.mantovi.MyFlux.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;
}
