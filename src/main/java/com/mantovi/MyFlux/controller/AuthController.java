package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.RegisterRequestDTO;
import com.mantovi.MyFlux.dto.LoginRequestDTO;
import com.mantovi.MyFlux.dto.ResponseDTO;
import com.mantovi.MyFlux.dto.UserResponseDTO;
import com.mantovi.MyFlux.infra.security.TokenService;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder  passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody LoginRequestDTO loginBody) {
        User user = this.userRepository.findByEmail(loginBody.email()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (passwordEncoder.matches(loginBody.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO registerBody){
        Optional<User> user = this.userRepository.findByEmail(registerBody.email());
        if(user.isEmpty()) {
             User newUser = new User();
             newUser.setPassword(passwordEncoder.encode(registerBody.password()));
             newUser.setEmail(registerBody.email());
             newUser.setUsername(registerBody.username());
             this.userRepository.save(newUser);

             String token = this.tokenService.generateToken(newUser);
             return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();

    }
}
