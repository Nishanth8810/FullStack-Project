package com.microservices.notificationservice.emailService;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpGenerator {
    public String generateOtp() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return String.format("%06d", randomNumber);
    }
}