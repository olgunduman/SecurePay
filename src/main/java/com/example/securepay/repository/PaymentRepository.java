package com.example.securepay.repository;

import com.example.securepay.entity.Customer;
import com.example.securepay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCustomer(Customer customer);
    List<Payment> findByPaymentDateBetween(Date startDate, Date endDate);


}
