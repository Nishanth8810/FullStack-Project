package com.microservices.recipesservice.repository;

import com.microservices.recipesservice.modal.Recipe;
import com.microservices.recipesservice.modal.SavedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedRecipeRepo extends JpaRepository<SavedRecipe, Long> {

    Optional<List<SavedRecipe>> findByUserId(Long id);

    Boolean existsByRecipeAndUserId(Recipe recipe, Long userId);
}
