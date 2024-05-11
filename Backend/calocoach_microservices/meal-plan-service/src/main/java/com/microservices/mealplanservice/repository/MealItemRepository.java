package com.microservices.mealplanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microservices.mealplanservice.modal.Meal;
import com.microservices.mealplanservice.modal.MealItem;

import java.util.List;

public interface MealItemRepository extends JpaRepository<MealItem, Long> {
    List<MealItem> findByMeal(Meal meal);
}
