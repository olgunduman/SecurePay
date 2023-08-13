package com.example.securepay.service.customer;
import com.example.securepay.dto.CustomerRequest;
import com.example.securepay.dto.CustomerResponse;
import com.example.securepay.entity.Card;
import com.example.securepay.entity.Customer;
import com.example.securepay.repository.CardRepository;
import com.example.securepay.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ModelMapper modelMapper;


    @Test
    public void whenCreateCustomer_thenSuccess() {
        // given
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("olgun duman")
                .email("olgunduman49@gmail.com")
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .name(customerRequest.getName())
                .email(customerRequest.getEmail()).build();
        when(customerRepository.save(customer)).thenReturn(customer);
        when(modelMapper.map(customerRequest, Customer.class)).thenReturn(customer);
        // when
        long response = customerServiceImpl.createCustomer(customerRequest);
        // then
        assertEquals(response, customer.getId());
    }

    @Test
    public void whenGetCustomerWithCardInfo_thenSuccess() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("olgun duman");
        customer.setEmail("olgunduman49@gmail.com");

        Card card = new Card();
        card.setCardNumber("1234567890123456");
        Card card2 = new Card();
        card2.setCardNumber("123");
       customer.setCard(List.of(card, card2));

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));


        CustomerResponse mockedCustomerResponse = new CustomerResponse();
        mockedCustomerResponse.setName(customer.getName());
        mockedCustomerResponse.setEmail(customer.getEmail());
        mockedCustomerResponse.setCardNumber(List.of(card.getCardNumber(), card2.getCardNumber()));

        when(modelMapper.map(customer, CustomerResponse.class)).thenReturn(mockedCustomerResponse);

        CustomerResponse customerResponse = customerServiceImpl.getCustomerWithCardInfo(customer.getId());

        assertNotNull(customerResponse);
        assertEquals(customer.getName(), customerResponse.getName());
        assertEquals(card.getCardNumber(), customerResponse.getCardNumber().get(0));
        assertEquals(card2.getCardNumber(),customerResponse.getCardNumber().get(1));


    }

    @Test
    public void createCustomer_whenEmailExist_thenThrowException() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("olgun duman")
                .email("olgunduman49@gmail.com")
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .name(customerRequest.getName())
                .email(customerRequest.getEmail()).build();

        doReturn(List.of(customer)).when(customerRepository).findAll();

        assertThrows(RuntimeException.class, () -> customerServiceImpl.createCustomer(customerRequest));


    }



}