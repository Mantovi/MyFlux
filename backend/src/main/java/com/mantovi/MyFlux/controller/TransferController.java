package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.transfer.TransferRequestDTO;
import com.mantovi.MyFlux.dto.transfer.TransferResponseDTO;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.TransferRepository;
import com.mantovi.MyFlux.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDTO> createTransfer(@RequestBody @Valid TransferRequestDTO request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transferService.createTransfer(request, user));
    }
}
