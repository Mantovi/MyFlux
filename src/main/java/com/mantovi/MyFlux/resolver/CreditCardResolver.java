package com.mantovi.MyFlux.resolver;

import com.mantovi.MyFlux.model.CreditCard;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardResolver {

    private final CreditCardRepository creditCardRepository;

    public CreditCard validateCreditCard(UUID cardId, User user) {
        return findAndValidateCreditCard(cardId, user);
    }

    private CreditCard findAndValidateCreditCard(UUID cardId, User user) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        if (!card.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Usuário sem acesso a esse cartão");
        }

        if (!card.getActive().equals(true)) {
            throw new RuntimeException("Esse cartão está desativado");
        }

        return card;
    }
}
