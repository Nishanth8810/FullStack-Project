package com.microservices.authenticationserver.dto.PlanDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlanResponse {
    private long id;
    private String planName;
    private LocalDateTime createdTime;
    private String description;
    private double amountControl;
    private double amountCollaborate;
    private int numberOfClients;
    private int list;
}
