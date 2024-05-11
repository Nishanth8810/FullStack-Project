package com.microservices.recipesservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRecipeReqDTO {
    private long recipeId;
    private String userEmail;
    private String reason;
}
