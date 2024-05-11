package com.microservices.mealplanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microservices.mealplanservice.modal.Client;
import com.microservices.mealplanservice.modal.MealPlan;

import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByClient(Client client);

    List<MealPlan> findAllByClientIn(List<Client> clients);
}
