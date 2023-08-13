package com.example.securepay.service.payment;

import com.example.securepay.dto.CustomerResponse;
import com.example.securepay.dto.PaymentResponse;
import com.example.securepay.entity.Card;
import com.example.securepay.entity.Customer;
import com.example.securepay.entity.Payment;
import com.example.securepay.repository.CardRepository;
import com.example.securepay.repository.CustomerRepository;
import com.example.securepay.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentServiceImpl;

    @Mock
    private  PaymentRepository paymentRepository;
    @Mock
    private  CustomerRepository customerRepository;
    @Mock
    private  CardRepository cardRepository;
    @Mock
    private  ModelMapper modelMapper;



    @Test
    public void whenCreatePayment_thenSuccess() {
        // given
        long customerId = 1L;
        BigDecimal amount = new BigDecimal(100);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer()));

        PaymentResponse mockedPaymentResponse = new PaymentResponse();
        mockedPaymentResponse.setAmount(amount);
        mockedPaymentResponse.setCustomerId(customerId);
        mockedPaymentResponse.setId(1L);
        mockedPaymentResponse.setPaymentDate(new Date());

     when(modelMapper.map(any(Payment.class), any())).thenReturn(mockedPaymentResponse);
        // when
        PaymentResponse response = paymentServiceImpl.createPayment(customerId, amount);
        // then
        assertEquals(response.getAmount(), amount);
        assertEquals(response.getCustomerId(), customerId);

    }

    @Test
    void CreatePayment_CustomerNotFound() {
        // given
        long customerId = 1L;
        BigDecimal amount = new BigDecimal(0);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

       EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            paymentServiceImpl.createPayment(customerId, amount);
        });

        assertEquals(exception.getMessage(), "Customer with ID " + customerId + " not found");

    }
    @Test
    public void testCreatePayment_InvalidAmount() {
        // Given

        BigDecimal amount = new BigDecimal("0");
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            paymentServiceImpl.createPayment(customer.getId(), amount);
        });
    }
}