package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.UserResponseDTO;
import com.mantovi.MyFlux.mapper.UserMapper;
import com.mantovi.MyFlux.repository.UserRepository;
import com.mantovi.MyFlux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseUser)
                .collect(Collectors.toList());
    }
}
