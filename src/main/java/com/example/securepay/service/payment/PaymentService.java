package com.example.securepay.service.payment;

import com.example.securepay.dto.PaymentResponse;
import com.example.securepay.entity.Payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PaymentService {
    public PaymentResponse createPayment(Long customerId, BigDecimal amount);
    public List<PaymentResponse> getPaymentsByCustomerOrCard(Long customerId, String cardNumber);


    public List<PaymentResponse> getPaymentsByDateRange(Date startDate, Date endDate);
}
