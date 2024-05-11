package com.microservices.authenticationserver.repository;

import com.microservices.authenticationserver.modal.userEntities.SavedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedRecipeRepo extends JpaRepository<SavedRecipe, Long> {
}
