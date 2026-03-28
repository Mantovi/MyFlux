package com.mantovi.MyFlux.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String email;
}
