package com.microservices.authenticationserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePassReq {
    @Schema(description = "New password for the user")
    private String newPassword;

    @Schema(description = "User's email address")
    private String email;

    @Schema(description = "Token sent to the user's email for verification")
    private String emailToken;
}