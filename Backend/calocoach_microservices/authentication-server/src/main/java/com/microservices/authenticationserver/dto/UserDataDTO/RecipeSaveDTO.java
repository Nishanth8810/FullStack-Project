package com.microservices.authenticationserver.dto.UserDataDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeSaveDTO {
    @Getter
    private String email;
    private long recipeId;

}
