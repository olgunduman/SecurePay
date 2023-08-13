package com.example.securepay.service.customer;

import com.example.securepay.dto.CustomerRequest;
import com.example.securepay.dto.CustomerResponse;
import com.example.securepay.entity.Card;
import com.example.securepay.entity.Customer;
import com.example.securepay.repository.CardRepository;
import com.example.securepay.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public long createCustomer(CustomerRequest customerRequest) {

        if(isEmailExist(customerRequest.getEmail())){
            log.error("Email already exist");
            throw new RuntimeException("Email already exist");
        }
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        customerRepository.save(customer);

        return customer.getId();

    }

    @Override
    public CustomerResponse getCustomerWithCardInfo(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + customerId + " not found"));


        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);

        log.info("CustomerResponse: {}", customerResponse);
        return customerResponse;


    }


    private boolean isEmailExist(String email) {
        var customerList= customerRepository.findAll();
        return customerList.stream().anyMatch(customer -> customer.getEmail().equals(email));
    }



}
