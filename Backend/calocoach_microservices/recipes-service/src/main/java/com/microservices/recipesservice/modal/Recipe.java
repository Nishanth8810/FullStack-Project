package com.microservices.recipesservice.modal;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "prep_time_minutes")
    private int prepTimeMinutes;
    @Column(name = "cook_time_minutes")
    private int cookTimeMinutes;
    private String imageUrl;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Direction> directions;
    @ElementCollection
    @CollectionTable(name = "nutrients", joinColumns = @JoinColumn(name = "recipe_id"))
    @MapKeyColumn(name = "nutrient_name")
    @Column(name = "nutrient_amount")
    private Map<String, Double> nutrients;
    private int unlisted;
    private long userId;
}
