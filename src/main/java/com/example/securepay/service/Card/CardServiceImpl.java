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


    @Override
    public CardResponse createCardForCustomer(Long customerId, String cardNumber) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

        Optional<Card> existingCard = cardRepository.findByCardNumber(cardNumber);
        if(existingCard.isPresent()){
            log.error("Card number already exist");
            throw new RuntimeException("Card number already exist");
        }
       isCardNumberExist(cardNumber);
        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setCustomer(customer);
        cardRepository.save(card);

        customer.getCard().add(card);
        customerRepository.save(customer);

        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(card.getId());
        cardResponse.setCardNumber(card.getCardNumber());
        cardResponse.setCustomer(card.getCustomer().getId());

        return cardResponse;


    }



    private boolean isCardNumberExist(String cardNumber) {
       var cardList = cardRepository.findAll();
       return cardList.stream().anyMatch(card -> card.getCardNumber().equals(cardNumber));
    }

}
