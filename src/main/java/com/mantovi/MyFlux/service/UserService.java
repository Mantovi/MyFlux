package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.UserResponseDTO;
import com.mantovi.MyFlux.model.Role;

import java.util.List;

public interface UserService {

    void addRoleToUser(String email, Role role);
    List<UserResponseDTO> findAll();
}
