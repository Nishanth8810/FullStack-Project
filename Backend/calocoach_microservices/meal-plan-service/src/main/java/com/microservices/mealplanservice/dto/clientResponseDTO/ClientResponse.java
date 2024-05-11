package com.microservices.mealplanservice.dto.clientResponseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientResponse {
    private long trainerId;
    private long userId;
    private LocalDateTime clientSince;
    private long clientId;
    private String dietType;
    private String userEmail;
}
