package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.card.CreditCardRequestDTO;
import com.mantovi.MyFlux.dto.card.CreditCardResponseDTO;
import com.mantovi.MyFlux.dto.card.CreditCardUpdateRequestDTO;
import com.mantovi.MyFlux.mapper.CreditCardMapper;
import com.mantovi.MyFlux.model.CreditCard;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.CreditCardRepository;
import com.mantovi.MyFlux.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<CreditCardResponseDTO> listCardsByUser(UUID userId){
        List<CreditCard> creditCard = creditCardRepository.findAllCardsByUserId(userId);
        return creditCard.stream()
                .map(creditCardMapper::toCreditCardResponse)
                .toList();
    }

    @Override
    public CreditCardResponseDTO update(UUID cardId, CreditCardUpdateRequestDTO request, User user) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Cartão de Crédito não encontrado"));

        creditCardMapper.updateCreditCard(card, request, user);
        CreditCard savedCard = creditCardRepository.save(card);
        return creditCardMapper.toCreditCardResponse(savedCard);
    }

    @Override
    public void deleteById(UUID cardId, User user) {
        CreditCard card = creditCardRepository.findById(cardId)
                        .orElseThrow(() -> new RuntimeException("Cartão de Crédito não encontrado"));

        if (!card.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso Negado");
        }

        creditCardRepository.deleteById(cardId);

    }
}
