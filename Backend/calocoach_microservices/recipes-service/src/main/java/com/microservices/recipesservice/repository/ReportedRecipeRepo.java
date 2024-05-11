package com.microservices.recipesservice.repository;

import com.microservices.recipesservice.modal.ReportedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedRecipeRepo extends JpaRepository<ReportedRecipe, Long> {
}
