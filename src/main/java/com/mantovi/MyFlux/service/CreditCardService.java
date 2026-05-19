package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.card.CreditCardRequestDTO;
import com.mantovi.MyFlux.dto.card.CreditCardResponseDTO;
import com.mantovi.MyFlux.mapper.CreditCardMapper;
import com.mantovi.MyFlux.model.User;

public interface CreditCardService {

    CreditCardResponseDTO createCreditCard(CreditCardRequestDTO request, User user);
}
