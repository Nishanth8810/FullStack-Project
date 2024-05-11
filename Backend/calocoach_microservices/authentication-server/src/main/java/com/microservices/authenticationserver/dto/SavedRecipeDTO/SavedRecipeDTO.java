package com.microservices.authenticationserver.dto.SavedRecipeDTO;

import com.microservices.authenticationserver.modal.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class SavedRecipeDTO {
    @Schema(description = "Unique identifier for the saved recipe", example = "12345")
    private Long id;
    @Schema(description = "User who saved the recipe", example = "john.doe@example.com")
    private User user;
    @Schema(description = "ID of the recipe that was saved", example = "67890")
    private long recipeId;
}