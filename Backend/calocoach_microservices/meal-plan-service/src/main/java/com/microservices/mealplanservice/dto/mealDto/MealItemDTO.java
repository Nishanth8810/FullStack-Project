package com.microservices.mealplanservice.dto.mealDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MealItemDTO {
    private Long recipeId;
    private String name;
    private String imageUrl;
    private Map<String, Double> nutrients;
}
