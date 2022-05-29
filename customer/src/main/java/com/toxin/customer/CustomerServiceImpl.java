package com.toxin.customer;

import com.toxin.clients.fraud.FraudCheckResponse;
import com.toxin.clients.fraud.FraudClient;
import com.toxin.clients.notification.NotificationClient;
import com.toxin.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final NotificationClient notificationClient;
    private final FraudClient fraudClient;

    public void register(CustomerRegistrationRequest request) {
         Customer customer = Customer.builder()
                 .firstName(request.firstName())
                 .lastName(request.lastName())
                 .email(request.email())
                 .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudResponse = fraudClient.isFraudster(customer.getId());

        if (Objects.requireNonNull(fraudResponse).isFraudster()) {
            throw new IllegalStateException("FRAUDSTER");
        }

        notificationClient.sendNotification(new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                "Hi %s %s".formatted(customer.getFirstName(), customer.getLastName())
        ));
    }
}
