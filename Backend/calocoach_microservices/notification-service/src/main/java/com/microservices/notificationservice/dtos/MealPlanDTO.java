package com.microservices.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class MealPlanDTO {
    private Long id;
    private Long trainerId;
    private String userEmail;
    private String trainerEmail;
    private String planName;
    private Long clientId;
    private String note;
    private LocalDate forDate;
    private List<MealDTO> meals;
    private Double totalCalories;
    private Long planId;
}
