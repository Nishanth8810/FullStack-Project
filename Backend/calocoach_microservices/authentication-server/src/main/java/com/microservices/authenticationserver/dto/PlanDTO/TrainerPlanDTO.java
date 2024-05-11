package com.microservices.authenticationserver.dto.PlanDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TrainerPlanDTO {
    @Schema(description = "ID of the plan", example = "1")
    private String id;
    @Schema(description = "Name of the plan", example = "Fitness Plan")
    private String planName;
    @Schema(description = "Amount of the plan", example = "100")
    private long amount;
}