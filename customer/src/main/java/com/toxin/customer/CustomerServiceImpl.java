package com.toxin.customer;

import com.toxin.amqp.RabbitMQMessageProducer;
import com.toxin.clients.fraud.FraudCheckResponse;
import com.toxin.clients.fraud.FraudClient;
import com.toxin.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final CustomerRepository customerRepository;
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

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                "Hi %s %s".formatted(customer.getFirstName(), customer.getLastName())
        );

        rabbitMQMessageProducer.publish(
                "internal.exchange",
                "internal.notification.routing-key",
                notificationRequest
        );
    }
}
