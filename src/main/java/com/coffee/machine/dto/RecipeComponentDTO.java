package com.coffee.machine.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class RecipeComponentDTO {
    private Long ingredientId;
    private Long subRecipeId;
    private BigDecimal quantity;
}
