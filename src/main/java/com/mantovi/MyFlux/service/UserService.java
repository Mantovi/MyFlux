package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.CreateUserRequestDTO;
import com.mantovi.MyFlux.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> findAll();

    UserResponseDTO createUser(CreateUserRequestDTO createDTO);
}
