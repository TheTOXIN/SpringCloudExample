package com.toxin.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {

}
