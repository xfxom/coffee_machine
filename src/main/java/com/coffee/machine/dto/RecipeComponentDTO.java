package com.coffee.machine.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RecipeComponentDTO {
    private Long ingredientId;
    private Long subRecipeId;
    @Positive(message = "Quantity must be greater than 0")
    private BigDecimal quantity;
}
