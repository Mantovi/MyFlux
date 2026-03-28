package com.mantovi.MyFlux.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO  {

    @NotBlank
    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    @Size(min = 8, message = "A senha deve possuir pele menos 8 caracteres")
    private String password;
}
