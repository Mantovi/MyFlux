package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.card.CreditCardRequestDTO;
import com.mantovi.MyFlux.dto.card.CreditCardResponseDTO;
import com.mantovi.MyFlux.mapper.CreditCardMapper;
import com.mantovi.MyFlux.model.CreditCard;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.CreditCardRepository;
import com.mantovi.MyFlux.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;


    @Override
    public CreditCardResponseDTO createCreditCard(CreditCardRequestDTO request, User user) {

        CreditCard card = creditCardMapper.toCreditCard(request, user);
        CreditCard savedCard = creditCardRepository.save(card);
        return creditCardMapper.toCreditCardResponse(savedCard);

    }
}
