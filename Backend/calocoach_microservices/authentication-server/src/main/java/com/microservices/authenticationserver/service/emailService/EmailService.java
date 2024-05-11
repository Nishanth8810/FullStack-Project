package com.microservices.authenticationserver.service.emailService;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EmailService {
    final private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSentDate((java.sql.Date.valueOf(LocalDate.now())));
        message.setFrom("eatTooMuch.com");
        message.setSubject("Verification OTP from eatTooMuch.com ");
        message.setSubject("OTP will expires in 2 minutes");
        message.setText("hi welcome to my eatTooMuch.com" + email);
        message.setText("Otp: " + otp);

        javaMailSender.send(message);
    }
}
