package com.mantovi.MyFlux.dto.card;

import java.util.UUID;

public record CreditCardResponseDTO(
   UUID id,
   String name,
   Integer closingDay,
   Integer dueDay,
   boolean active
) {}
