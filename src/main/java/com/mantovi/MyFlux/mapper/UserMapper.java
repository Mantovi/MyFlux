package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.UserResponseDTO;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseUser(User user) {
        if (user == null) return null;

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}