package com.coffee.machine.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "recipe_components")
@NoArgsConstructor
@AllArgsConstructor
public class RecipeComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "sub_recipe_id")
    private Recipe subRecipe;

    @Column(nullable = false)
    private BigDecimal quantity;
}
