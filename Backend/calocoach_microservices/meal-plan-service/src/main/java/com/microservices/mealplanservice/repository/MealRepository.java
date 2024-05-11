package com.microservices.mealplanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microservices.mealplanservice.modal.Meal;
import com.microservices.mealplanservice.modal.MealPlan;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByMealPlan(MealPlan mealPlan);
}
