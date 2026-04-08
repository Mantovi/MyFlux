package com.mantovi.MyFlux.dto;

import com.mantovi.MyFlux.model.Role;

import java.util.Set;
import java.util.UUID;

public record UserResponseDTO (
        UUID id,
        String username,
        String email,
        Set<Role> roles
) {}
