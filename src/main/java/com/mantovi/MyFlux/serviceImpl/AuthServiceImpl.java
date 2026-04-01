package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.LoginRequestDTO;
import com.mantovi.MyFlux.dto.RegisterRequestDTO;
import com.mantovi.MyFlux.dto.ResponseDTO;
import com.mantovi.MyFlux.infra.security.TokenService;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.UserRepository;
import com.mantovi.MyFlux.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public ResponseDTO login(LoginRequestDTO loginBody) {
        User user = userRepository.findByEmail(loginBody.email())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if(!passwordEncoder.matches(loginBody.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        String token = this.tokenService.generateToken(user);
        return new ResponseDTO(user.getUsername(), token);
    }

    @Override
    public ResponseDTO register(RegisterRequestDTO registerBody) {
        Optional<User> user = userRepository.findByEmail(registerBody.email());
        if(user.isPresent()) {
            throw new UsernameNotFoundException("User Already Exists");
        }
        User userEntity = new User();
        userEntity.setUsername(registerBody.username());
        userEntity.setEmail(registerBody.email());
        userEntity.setPassword(passwordEncoder.encode(registerBody.password()));

        userRepository.save(userEntity);
        String token = tokenService.generateToken(userEntity);
        return new ResponseDTO(userEntity.getUsername(), token);
    }
}
