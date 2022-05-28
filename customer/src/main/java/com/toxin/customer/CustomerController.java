package com.toxin.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public record CustomerController(
        CustomerServiceImpl customerService
) {

    @PostMapping
    private void register(@RequestBody CustomerRegistrationRequest request) {
        log.info("New customer registration {}", request);
        customerService.register(request);
    }
}
