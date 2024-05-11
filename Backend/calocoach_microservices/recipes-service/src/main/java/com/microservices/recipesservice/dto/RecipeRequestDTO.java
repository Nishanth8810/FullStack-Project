package com.microservices.recipesservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RecipeRequestDTO {
    private String name;
    private int prepTimeMinutes;
    private int cookTimeMinutes;
    private List<IngredientDTO> ingredients;
    private List<DirectionDTO> directionList;
    private String imageUrl;
    private Long authorId;
    private Map<String, Double> nutrients;
    private int calories;
    private int carbs;
    private int fats;
    private int proteins;
}