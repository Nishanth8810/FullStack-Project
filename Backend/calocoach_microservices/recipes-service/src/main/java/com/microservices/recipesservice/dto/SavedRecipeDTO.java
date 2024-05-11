package com.microservices.recipesservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavedRecipeDTO {
    private Long id;
    private long user;
    private long recipeId;
}