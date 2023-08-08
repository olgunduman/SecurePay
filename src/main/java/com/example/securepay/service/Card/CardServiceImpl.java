package com.example.securepay.service.Card;

import com.example.securepay.dto.CardResponse;
import com.example.securepay.entity.Card;
import com.example.securepay.entity.Customer;
import com.example.securepay.repository.CardRepository;
import com.example.securepay.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;


    @Override
    public CardResponse createCardForCustomer(Long customerId, String cardNumber) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

        Optional<Card> existingCard = cardRepository.findByCustomer(customer);
        if (existingCard.isPresent()) {
            log.error("Card already exists for this customer");
            throw new IllegalArgumentException("Card for customer " + customer.getName() + " already exists");
        }

        cardNumberControl(cardNumber);

        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setCustomer(customer);

        cardRepository.save(card);
        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(card.getId());
        cardResponse.setCardNumber(card.getCardNumber());
        cardResponse.setCustomer(card.getCustomer().getId());

        return cardResponse;


    }

    private void cardNumberControl(String cardNumber) {
        if(isCardNumberExist(cardNumber)){
            log.error("Card number already exist");
            throw new RuntimeException("Card number already exist");
        }
    }

    private boolean isCardNumberExist(String cardNumber) {
       var cardList = cardRepository.findAll();
       return cardList.stream().anyMatch(card -> card.getCardNumber().equals(cardNumber));
    }
}
