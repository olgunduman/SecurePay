package com.example.securepay.service.Card;

import com.example.securepay.dto.CardResponse;
import com.example.securepay.entity.Card;

public interface CardService {

    public CardResponse createCardForCustomer(Long customerId, String cardNumber);
}
