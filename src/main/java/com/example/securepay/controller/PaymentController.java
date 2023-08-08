package com.example.securepay.controller;

import com.example.securepay.dto.PaymentResponse;
import com.example.securepay.entity.Payment;
import com.example.securepay.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/search")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByCustomerOrCard(
            @RequestParam(required = false) Long customerId,
            @RequestParam (required = false) String cardNumber
    ) {
        List<PaymentResponse> payments = paymentService.getPaymentsByCustomerOrCard(customerId, cardNumber);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestParam Long customerId, @RequestParam BigDecimal amount) {
        PaymentResponse createdPayment = paymentService.createPayment(customerId, amount);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/payments-by-date-range")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<PaymentResponse> payments = paymentService.getPaymentsByDateRange(startDate, endDate);

        return ResponseEntity.ok(payments);
    }

}