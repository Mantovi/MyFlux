package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.CreateUserRequestDTO;
import com.mantovi.MyFlux.dto.UserResponseDTO;
import com.mantovi.MyFlux.mapper.UserMapper;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.UserRepository;
import com.mantovi.MyFlux.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public List<UserResponseDTO> getAll(){
        return userService.findAll();
    }

    @PostMapping("/register")
    public UserResponseDTO createUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO){
        return userService.createUser(createUserRequestDTO);
    }

}
