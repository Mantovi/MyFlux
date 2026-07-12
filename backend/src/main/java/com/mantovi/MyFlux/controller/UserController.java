package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.UserResponseDTO;
import com.mantovi.MyFlux.mapper.UserMapper;
import com.mantovi.MyFlux.model.Role;
import com.mantovi.MyFlux.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public List<UserResponseDTO> findAll() {
        return this.userService.findAll();
    }

    @PostMapping("/{email}/roles")
    public ResponseEntity<?> addRoleToUser(@PathVariable String email, @RequestBody Role role) {
        this.userService.addRoleToUser(email, role);
        return ResponseEntity.ok().build();
    }

}
