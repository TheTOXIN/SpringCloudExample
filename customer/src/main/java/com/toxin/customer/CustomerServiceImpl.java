package com.toxin.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void register(CustomerRegistrationRequest request) {
         Customer customer = Customer.builder()
                 .firstName(request.firstName())
                 .lastName(request.lastName())
                 .email(request.email())
                 .build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if (Objects.requireNonNull(fraudResponse).isFraudster()) {
            throw new IllegalStateException("FRAUDSTER");
        }
    }
}
