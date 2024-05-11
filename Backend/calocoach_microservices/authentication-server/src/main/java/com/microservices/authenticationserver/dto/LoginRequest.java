package com.microservices.authenticationserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;
    @Schema(description = "User's password", example = "password123")
    private String password;
}
