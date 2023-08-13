package com.example.securepay.dto;

import com.example.securepay.entity.Customer;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponse {
    private Long id;


    private long customer;

    private String cardNumber;
}
