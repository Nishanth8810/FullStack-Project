package com.microservices.notificationservice.dtos;

import com.microservices.notificationservice.enums.MealType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MealDTO {
    private MealType mealType;
    private List<MealItemDTO> mealItems;

}
