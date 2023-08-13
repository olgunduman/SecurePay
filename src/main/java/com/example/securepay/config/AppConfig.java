package com.example.securepay.config;

import com.example.securepay.dto.CustomerResponse;
import com.example.securepay.dto.PaymentResponse;
import com.example.securepay.entity.Customer;
import com.example.securepay.entity.Payment;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    private static final Converter<Customer, CustomerResponse> CUSTOMER_TO_CUSTOMER_RESPONSE_CONVERTER =
            context -> new CustomerResponse(
                    context.getSource().getName(),
                    context.getSource().getEmail(),
                    context.getSource().getCard().stream()
                            .map(card-> card
                                    .getCardNumber()).collect(Collectors.toList()));

    private static final Converter<Payment, PaymentResponse> PAYMENT_TO_PAYMENT_RESPONSE_CONVERTER =
            context -> new PaymentResponse(
                    context.getSource().getId(),
                    context.getSource().getCustomer().getId(),
                    context.getSource().getAmount(),
                    context.getSource().getPaymentDate());
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(CUSTOMER_TO_CUSTOMER_RESPONSE_CONVERTER, Customer.class, CustomerResponse.class);
        modelMapper.addConverter(PAYMENT_TO_PAYMENT_RESPONSE_CONVERTER, Payment.class, PaymentResponse.class);
        return modelMapper;
    }
}