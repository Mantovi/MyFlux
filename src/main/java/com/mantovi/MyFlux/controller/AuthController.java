package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.RegisterRequestDTO;
import com.mantovi.MyFlux.dto.LoginRequestDTO;
import com.mantovi.MyFlux.dto.ResponseDTO;
import com.mantovi.MyFlux.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO loginBody) {
        ResponseDTO response = authService.login(loginBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO registerBody) {
        ResponseDTO response = authService.register(registerBody);
        return ResponseEntity.ok(response);
    }
}
