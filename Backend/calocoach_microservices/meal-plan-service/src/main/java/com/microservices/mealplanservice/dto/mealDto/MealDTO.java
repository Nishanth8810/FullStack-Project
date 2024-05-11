package com.microservices.mealplanservice.dto.mealDto;

import lombok.Getter;
import lombok.Setter;
import com.microservices.mealplanservice.enums.MealType;

import java.util.List;

@Getter
@Setter
public class MealDTO {
    private MealType mealType;
    private List<MealItemDTO> mealItems;
}
