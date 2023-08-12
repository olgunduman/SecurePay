package com.example.securepay.service.customer;

import com.example.securepay.dto.CustomerRequest;
import com.example.securepay.dto.CustomerResponse;
import com.example.securepay.entity.Card;
import com.example.securepay.entity.Customer;
import com.example.securepay.repository.CardRepository;
import com.example.securepay.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CustomerServiceImplTest {

    private CustomerServiceImpl customerServiceImpl;

    private CustomerRepository customerRepository;
    private CardRepository cardRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        cardRepository = Mockito.mock(CardRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        customerServiceImpl = new CustomerServiceImpl(
                customerRepository,
                cardRepository,
                modelMapper
        );
    }

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


        // when
        when(customerRepository.save(customer)).thenReturn(customer);
        when(modelMapper.map(customerRequest, Customer.class)).thenReturn(customer);

        // then
        customerServiceImpl.createCustomer(customerRequest);
        assertThat(customer.getName()).isEqualTo(customerRequest.getName());


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
        customer.setCard(card);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cardRepository.findById(card.getId())).thenReturn(Optional.of(card));

        CustomerResponse mockedCustomerResponse = new CustomerResponse();
        mockedCustomerResponse.setName(customer.getName());
        mockedCustomerResponse.setEmail(customer.getEmail());
        mockedCustomerResponse.setCardNumber(card.getCardNumber());

        when(modelMapper.map(customer, CustomerResponse.class)).thenReturn(mockedCustomerResponse);

        CustomerResponse customerResponse = customerServiceImpl.getCustomerWithCardInfo(customer.getId());

        assertNotNull(customerResponse);
        assertEquals(customer.getName(), customerResponse.getName());
        assertEquals(card.getCardNumber(), customerResponse.getCardNumber());



    }
}