package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.account.AccountRequestDTO;
import com.mantovi.MyFlux.dto.account.AccountResponseDTO;
import com.mantovi.MyFlux.dto.account.AccountUpdateRequestDTO;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid AccountRequestDTO accountRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.create(accountRequest, user));
    }

    @PatchMapping("/update/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable UUID accountId, @RequestBody @Valid AccountUpdateRequestDTO accountRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.update(accountId, accountRequest, user));
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId, @AuthenticationPrincipal User user) {
        accountService.deleteById(accountId, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable UUID accountId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.getAccountById(accountId, user));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.getAllAccounts(user));
    }
}