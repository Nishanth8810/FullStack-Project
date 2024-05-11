package com.microservices.mealplanservice.dto.mealDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MealPlanRequestDTO {
    private Long clientId;
    private Long trainerId;
    private String planName;
    private LocalDate forDate;
    private String note;
    private List<MealDTO> meals;
    private Double totalCalories;
    private String trainerEmail;
}
