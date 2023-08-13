package com.example.securepay.dto;

import com.example.securepay.entity.Customer;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long id;
    private long customerId;
    private BigDecimal amount;
    private Date paymentDate;
}
