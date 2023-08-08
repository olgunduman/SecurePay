package com.example.securepay.controller;

import com.example.securepay.dto.CustomerRequest;
import com.example.securepay.dto.CustomerResponse;
import com.example.securepay.entity.Customer;
import com.example.securepay.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/create")
    public ResponseEntity<Long> createCustomer(@RequestBody CustomerRequest customerRequest) {
        Long createdCustomer = customerService.createCustomer(customerRequest);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerWithCardInfo(@PathVariable Long customerId) {
        CustomerResponse customer = customerService.getCustomerWithCardInfo(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
