package com.microservices.authenticationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationService {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationService.class, args);
    }
}
