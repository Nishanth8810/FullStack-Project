package com.microservices.recipesservice.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SavedRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long userId;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
