package com.example.securepay.service.customer;

import com.example.securepay.dto.CustomerRequest;
import com.example.securepay.dto.CustomerResponse;
import com.example.securepay.entity.Customer;

public interface CustomerService {
    public long createCustomer(CustomerRequest customerRequest);
    public CustomerResponse getCustomerWithCardInfo(Long customerId);


}
