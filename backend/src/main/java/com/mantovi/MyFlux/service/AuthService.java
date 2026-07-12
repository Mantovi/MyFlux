package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.authentication.LoginRequestDTO;
import com.mantovi.MyFlux.dto.authentication.RegisterRequestDTO;
import com.mantovi.MyFlux.dto.ResponseDTO;

public interface AuthService {


    ResponseDTO login(LoginRequestDTO loginBody);

    ResponseDTO register(RegisterRequestDTO registerBody);
}
