package com.microservices.recipesservice.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String direction;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
