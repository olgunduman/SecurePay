package com.example.securepay.service.Card;

import com.example.securepay.dto.CardResponse;
import com.example.securepay.dto.CustomerRequest;
import com.example.securepay.entity.Card;
import com.example.securepay.entity.Customer;
import com.example.securepay.repository.CardRepository;
import com.example.securepay.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @InjectMocks
    private CardServiceImpl cardServiceImpl;
    @Mock
    private  CardRepository cardRepository;
    @Mock
    private  CustomerRepository customerRepository;

    @Test
    public void whenCreateCardForCustomer_thenSuccess() {
        // given
        Long customerId = 1L;
        String cardNumber = "1234567890123456";
        // when
        // then
    }

    @Test
    public void createCardForCustomer_whenCustomerId_thenThrowException() {
           // given
        Customer customer = Customer.builder()
                .id(1L)
                .name("olgun duman")
                .email("olgun@gmail.com").build();
        Card card = Card.builder()
                .id(2L)
                .customer(customer)
                .cardNumber("1234567890123456").build();

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

       assertThrows(RuntimeException.class, () -> cardServiceImpl.createCardForCustomer(customer.getId(),
               card.getCardNumber()));

    }
    @Test
    void testCreateCardForCustomer_whenCardNumberExists() {
        // Arrange
       Customer customer = Customer.builder()
                .id(1L).build();
        Card card = new Card();
        card.setId(1L);
        card.setCardNumber("1234567890123456");
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(Optional.of(card));

        // then
        assertThrows(RuntimeException.class, () -> cardServiceImpl.createCardForCustomer(customer.getId(), card.getCardNumber()));
    }

    @Test
    void testCreateCardForCustomer() {
        // Arrange
        Customer customer = Customer.builder().id(1L).card(new ArrayList<>()).build();
        Card card = new Card();
        card.setId(1L);
        card.setCardNumber("1234567890123456");
        card.setCustomer(customer);
        customer.getCard().add(card);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(Optional.empty());
        doReturn(card).when(cardRepository).save(any(Card.class));
        doReturn(customer).when(customerRepository).save(any(Customer.class));
        CardResponse response = cardServiceImpl.createCardForCustomer(customer.getId(), card.getCardNumber());
        assertEquals(response.getCardNumber(), card.getCardNumber());
    }
    @Test
    public void createCardForCustomer_whenCardNumberControl_thenThrowException() {

    }


}