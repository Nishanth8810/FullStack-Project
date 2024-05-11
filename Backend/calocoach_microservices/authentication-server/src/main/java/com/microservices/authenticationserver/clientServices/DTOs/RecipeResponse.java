package com.microservices.authenticationserver.clientServices.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RecipeResponse {
    private long savedId;
    private long id;
    private String name;
    private int prepTimeMinutes;
    private int cookTimeMinutes;
    private List<IngredientDTO> ingredients;
    private List<DirectionDTO> directionList;
    private String imageUrl;
    //    private Long authorId;
    private Map<String, Double> nutrients;
    private int unList;


}
