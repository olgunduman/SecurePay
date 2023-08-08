package com.example.securepay.repository;


import com.example.securepay.entity.Card;
import com.example.securepay.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByCustomer(Customer customer);

    Optional<Card> findByCardNumber(String cardNumber);
}
