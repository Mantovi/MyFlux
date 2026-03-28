package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.CreateUserRequestDTO;
import com.mantovi.MyFlux.dto.UserResponseDTO;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toRequestUser(CreateUserRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        return User.builder()
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .build();
    }

    public UserResponseDTO toResponseUser(User user) {
        if (user == null) return null;

        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
