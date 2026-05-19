package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.card.CreditCardRequestDTO;
import com.mantovi.MyFlux.dto.card.CreditCardResponseDTO;
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
