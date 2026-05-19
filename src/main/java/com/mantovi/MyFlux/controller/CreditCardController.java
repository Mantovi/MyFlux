package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.card.CreditCardRequestDTO;
import com.mantovi.MyFlux.dto.card.CreditCardResponseDTO;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.service.CreditCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping("/create")
    public ResponseEntity<CreditCardResponseDTO> createCard(@RequestBody @Valid CreditCardRequestDTO request,
                                                            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.createCreditCard(request, user));
    }
}
