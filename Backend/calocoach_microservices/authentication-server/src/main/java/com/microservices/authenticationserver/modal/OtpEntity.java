package com.microservices.authenticationserver.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OtpEntity {
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String otp;
    private boolean otpActive;
    private LocalDateTime otpGeneratedTime;
}
