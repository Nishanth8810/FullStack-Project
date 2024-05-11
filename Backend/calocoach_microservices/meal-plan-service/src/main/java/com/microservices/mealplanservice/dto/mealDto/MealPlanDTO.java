package com.microservices.mealplanservice.dto.mealDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class MealPlanDTO {
    private Long id;
    @Schema(name = "trainerId", description = "id of the trainer", example = "1")
    private Long trainerId;
    @Schema(name = "trainerEmail", description = "Email of the trainer", example = "trainer@example.com")
    private String trainerEmail;
    @Schema(name = "planName", description = "Name of the meal plan", example = "Healthy Eating Plan")
    private String planName;
    @Schema(name = "clientId", description = "id of the client", example = "2")
    private Long clientId;
    @Schema(name = "note", description = "Additional notes for the meal plan", example = "Avoid nuts")
    private String note;
    @Schema(name = "forDate", description = "Date for the meal plan", example = "2022-12-31")
    private LocalDate forDate;
    @Schema(name = "meals", description = "List of meals in the meal plan")
    private List<MealDTO> meals;
    @Schema(name = "totalCalories", description = "Total calories for the meal plan", example = "1500.0")
    private Double totalCalories;
    @Schema(name = "planId", description = "ID of the plan", example = "3")
    private Long planId;
}