package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.card.CreditCardRequestDTO;
import com.mantovi.MyFlux.dto.card.CreditCardResponseDTO;
import com.mantovi.MyFlux.dto.card.CreditCardUpdateRequestDTO;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.service.CreditCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/list")
    public ResponseEntity<List<CreditCardResponseDTO>> listCards(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.listCardsByUser(user.getId()));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CreditCardResponseDTO> getCard(@PathVariable UUID cardId,
                                                         @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.getCardById(cardId));
    }

    @PatchMapping("/update/{cardId}")
    public ResponseEntity<CreditCardResponseDTO> updateCard(@PathVariable UUID cardId,
                                                            @RequestBody @Valid CreditCardUpdateRequestDTO request,
                                                            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(creditCardService.update(cardId, request, user));
    }

    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable UUID cardId,
                                           @AuthenticationPrincipal User user) {
        creditCardService.deleteById(cardId, user);
        return ResponseEntity.ok().build();
    }
}
