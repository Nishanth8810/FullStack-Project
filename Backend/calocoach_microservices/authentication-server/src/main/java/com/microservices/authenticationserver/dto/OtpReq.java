package com.microservices.authenticationserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpReq {
    private String otpNumber;
    private String email;
}
