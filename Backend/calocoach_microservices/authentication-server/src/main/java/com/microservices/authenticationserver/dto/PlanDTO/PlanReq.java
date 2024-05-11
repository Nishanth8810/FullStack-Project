package com.microservices.authenticationserver.dto.PlanDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlanReq {
    @Schema(description = "ID of the plan", example = "1")
    private long id;
    @Schema(description = "Name of the plan", example = "Weight Loss Plan")
    private String planName;
    @Schema(description = "Time when the plan was created", example = "2022-09-15T10:00:00")
    private LocalDateTime createdTime;
    @Schema(description = "Description of the plan", example = "Customized meal plan for weight loss")
    private String description;
    @Schema(description = "Amount of control in the plan", example = "0.8")
    private double amountControl;
    @Schema(description = "Amount of collaboration in the plan", example = "0.2")
    private double amountCollaborate;
    @Schema(description = "Number of clients enrolled in the plan", example = "10")
    private int numberOfClients;
}