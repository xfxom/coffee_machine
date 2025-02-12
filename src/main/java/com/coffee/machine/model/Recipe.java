package com.coffee.machine.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Компоненты рецепта (составляющие)
    /*
    * {
    *   "name": "cappuccino",
    *   "components": [
    *     {
    *       "subRecipe": { "id": <id_espresso> },
    *       "quantity": 2
    *     },
    *     {
    *       "ingredient": { "id": <id_milk> },
    *       "quantity": 1
    *     }
    *   ]
    * }
    */

    @JsonManagedReference
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeComponent> components;
}
