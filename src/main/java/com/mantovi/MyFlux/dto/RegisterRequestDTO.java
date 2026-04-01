package com.mantovi.MyFlux.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

    @NotBlank
    String username,

    @NotBlank
    @Email(message = "Email inválido")
    String email,

    @NotBlank
    @Size(min = 8, message = "A senha deve possuir pele menos 8 caracteres")
    String password

){}
