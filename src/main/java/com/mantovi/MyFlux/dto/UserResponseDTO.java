package com.mantovi.MyFlux.dto;

import java.util.UUID;

public record UserResponseDTO (UUID id, String username, String email) {

}
