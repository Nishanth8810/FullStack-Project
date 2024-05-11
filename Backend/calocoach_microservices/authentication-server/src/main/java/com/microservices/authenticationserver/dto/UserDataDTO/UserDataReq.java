package com.microservices.authenticationserver.dto.UserDataDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDataReq {
    @Schema(description = "List of food exclusions for the user", example = "[\"nuts\",\"dairy\"]")
    private List<String> exclusions;
    @Schema(description = "Preferable diet type for the user", example = "Vegetarian")
    private String preDiet;
    @Schema(description = "User's goal (e.g., weight loss, muscle gain)", example = "Weight loss")
    private String goal;
    @Schema(description = "User's weight in kilograms", example = "70.5")
    private double weight;
    @Schema(description = "User's height in centimeters", example = "175.0")
    private double height;
    @Schema(description = "User's gender (Male/Female/Other)", example = "Male")
    private String gender;
    @Schema(description = "User's age", example = "30")
    private int age;
    @Schema(description = "User's body fat percentage", example = "20.5")
    private double bodyFat;
    @Schema(description = "User's activity level", example = "Moderate")
    private String activityLevel;
    @Schema(description = "User's email address", example = "user@example.com")
    private String userEmail;
}