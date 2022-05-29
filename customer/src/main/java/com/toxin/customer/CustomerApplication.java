package com.toxin.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(
        basePackages = "com.toxin.clients"
)
@EnableEurekaClient
@SpringBootApplication(
        scanBasePackages = {
                "com.toxin.customer",
                "com.toxin.amqp",
        }
)
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
