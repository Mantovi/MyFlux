package com.mantovi.MyFlux.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO  {

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Min(8)
    @Column(nullable = false)
    private String password;
}
