package com.toxin.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/fraud")
@AllArgsConstructor
public class FraudController {

    private final FraudService fraudService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        log.info("Fraud check request for customer {}", customerId);
        return new FraudCheckResponse(
                fraudService.isFraudCustomer(customerId)
        );
    }
}
