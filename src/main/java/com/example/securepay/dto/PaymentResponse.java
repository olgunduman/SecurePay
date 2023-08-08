package com.example.securepay.dto;

import com.example.securepay.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long id;
    private long customerId;
    private BigDecimal amount;
    private Date paymentDate;
}
