package com.microservices.recipesservice.repository;

import com.microservices.recipesservice.modal.Recipe;
import com.microservices.recipesservice.modal.SavedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);

    List<Recipe> findByNameContainingIgnoreCase(String query);

    Optional<List<SavedRecipe>> findByUserId(Long id);
}