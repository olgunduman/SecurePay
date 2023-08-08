package com.example.securepay.controller;

import com.example.securepay.dto.CardResponse;
import com.example.securepay.entity.Card;
import com.example.securepay.service.Card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/create")
    public ResponseEntity<CardResponse> createCardForCustomer(@RequestParam @Positive Long customerId, @RequestParam String cardNumber) {
        CardResponse createdCard = cardService.createCardForCustomer(customerId, cardNumber);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }
}
