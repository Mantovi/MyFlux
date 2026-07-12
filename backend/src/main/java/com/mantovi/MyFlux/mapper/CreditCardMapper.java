package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.card.CreditCardRequestDTO;
import com.mantovi.MyFlux.dto.card.CreditCardResponseDTO;
import com.mantovi.MyFlux.dto.card.CreditCardUpdateRequestDTO;
import com.mantovi.MyFlux.model.CreditCard;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

@Component
public class CreditCardMapper {

    public CreditCard toCreditCard(CreditCardRequestDTO request, User user) {
        return CreditCard.builder()
                .name(request.name())
                .closingDay(request.closingDay())
                .dueDay(request.dueDay())
                .active(true)
                .user(user)
                .build();
    }

    public void updateCreditCard(CreditCard card, CreditCardUpdateRequestDTO request, User user) {
        if (request.name() != null) {
            card.setName(request.name());
        }
        if (request.closingDay() != null) {
            card.setClosingDay(request.closingDay());
        }
        if (request.dueDay() != null) {
            card.setDueDay(request.dueDay());
        }
        if (request.active() != null) {
            card.setActive(request.active());
        }
        if (user != null) {
            card.setUser(user);
        }
    }

    public CreditCardResponseDTO toCreditCardResponse(CreditCard creditCard) {
        return new CreditCardResponseDTO(
                creditCard.getId(),
                creditCard.getName(),
                creditCard.getClosingDay(),
                creditCard.getDueDay(),
                creditCard.getActive()
        );
    }
}
