package com.mantovi.MyFlux.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @Column(unique = true, nullable = false)
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    @Column(nullable = false)
    private String password;
}
