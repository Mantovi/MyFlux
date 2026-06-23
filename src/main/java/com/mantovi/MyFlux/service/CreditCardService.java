package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.card.CreditCardRequestDTO;
import com.mantovi.MyFlux.dto.card.CreditCardResponseDTO;
import com.mantovi.MyFlux.dto.card.CreditCardUpdateRequestDTO;
import com.mantovi.MyFlux.mapper.CreditCardMapper;
import com.mantovi.MyFlux.model.User;

import java.util.List;
import java.util.UUID;

public interface CreditCardService {

    CreditCardResponseDTO createCreditCard(CreditCardRequestDTO request, User user);

    List<CreditCardResponseDTO> listCardsByUser(UUID userId);

    CreditCardResponseDTO update(UUID cardId, CreditCardUpdateRequestDTO request, User user);

    void deleteById(UUID cardId, User user);
}
