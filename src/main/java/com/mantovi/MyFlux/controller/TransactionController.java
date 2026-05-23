package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @RequestBody @Valid TransactionRequestDTO request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.createTransaction(request, user));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String description) {

        return ResponseEntity.ok(transactionService.findAllFromUser(user.getId(), description));
    }
}
