package com.mantovi.MyFlux.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank
        @Email(message = "Email inválido")
        String email,

        @NotBlank
        @Size(min = 8, message = "sua senha deve possuir pelo menos de 8 caracteres")
        String password) {

}
