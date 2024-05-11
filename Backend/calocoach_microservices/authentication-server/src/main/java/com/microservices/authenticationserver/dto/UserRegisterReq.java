package com.microservices.authenticationserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterReq {
    @Schema(description = "User's first name", example = "John")
    private String firstname;
    @Schema(description = "User's last name", example = "Doe")
    private String lastname;
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;
    @Schema(description = "User's password", example = "password123")
    private String password;
}