package com.microservices.mealplanservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConnectionReqDto {
    private long userId;
    private long trainerId;
}
