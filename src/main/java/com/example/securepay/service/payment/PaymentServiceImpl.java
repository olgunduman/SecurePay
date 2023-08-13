package com.example.securepay.service.payment;

import com.example.securepay.dto.PaymentResponse;
import com.example.securepay.entity.Card;
import com.example.securepay.entity.Customer;
import com.example.securepay.entity.Payment;
import com.example.securepay.repository.CardRepository;
import com.example.securepay.repository.CustomerRepository;
import com.example.securepay.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;
    public PaymentResponse createPayment(Long customerId, BigDecimal amount) {
        log.info("PaymentRequest: customerId: {}, amount: {}", customerId, amount);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + customerId + " not found"));

        // Ödeme tutarı 0'dan büyük olmalı
        log.info("Amount control : {}", amount.compareTo(BigDecimal.ZERO));
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setAmount(amount);
        payment.setPaymentDate(new Date());

        paymentRepository.save(payment);
        log.info("Payment saved: {}", payment);
        PaymentResponse response = modelMapper.map(payment, PaymentResponse.class);
        log.info("PaymentResponse: {}", response);
      return response;

    }
    @Override
    public List<PaymentResponse> getPaymentsByCustomerOrCard(Long customerId, String cardNumber) {
        log.info("PaymentRequest: customerId: {}, cardNumber: {}", customerId, cardNumber);
    List<Payment> payments = new ArrayList<>();

        cardNumberControl(cardNumber, payments);
        customerIdControl(customerId, payments);
         log.info("Payments: {}", payments);
    List<PaymentResponse> paymentResponses = payments.stream()
            .map(payment -> modelMapper.map(payment, PaymentResponse.class))
            .distinct()
            .collect(Collectors.toList());
        log.info("PaymentResponses: {}", paymentResponses);
    return paymentResponses;

    }

    private void cardNumberControl(String cardNumber, List<Payment> payments) {
        if (cardNumber != null && !cardNumber.isEmpty()) {
            Optional<Card> card = cardRepository.findByCardNumber(cardNumber);
            if (card.isPresent()) {
                payments.addAll(paymentRepository.findByCustomer(card.get().getCustomer()));
            }
        }
    }


    private void customerIdControl(Long customerId, List<Payment> payments) {
        if (customerId != null && customerId > 0){
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            payments.addAll(paymentRepository.findByCustomer(customer.get()));
        }
    }
}
    @Override
    public List<PaymentResponse> getPaymentsByDateRange(Date startDate, Date endDate) {
        log.info("PaymentRequest: startDate: {}, endDate: {}", startDate, endDate);
        List<Payment> byPaymentDateBetween = paymentRepository.findByPaymentDateBetween(startDate, endDate);
        List<PaymentResponse> paymentResponses = byPaymentDateBetween.stream()
                .map(payment -> modelMapper.map(payment, PaymentResponse.class))
                .distinct()
                .collect(Collectors.toList());
        log.info("PaymentResponses: {}", paymentResponses);
        return paymentResponses;
}
}
