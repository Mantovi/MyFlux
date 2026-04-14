package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.AccountRequestDTO;
import com.mantovi.MyFlux.dto.AccountResponseDTO;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid AccountRequestDTO accountRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.create(accountRequest, user));
    }
}