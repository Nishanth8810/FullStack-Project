package com.microservices.mealplanservice.dto.mealDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MealPlanResponse {
    private String userEmail;
    private String planName;
    private LocalDate forDate;
    private Double totalCalories;
    private LocalDate created;
    private Long planId;
}
