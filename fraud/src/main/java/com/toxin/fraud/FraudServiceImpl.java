package com.toxin.fraud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudServiceImpl implements FraudService {

    private final FraudRepository fraudRepository;

    @Override
    public boolean isFraudCustomer(Integer costumerId) {
        fraudRepository.save(
                Fraud.builder()
                        .customerId(costumerId)
                        .createdAt(LocalDateTime.now())
                        .isFraudster(false)
                        .build()
        );
        return false;
    }
}
