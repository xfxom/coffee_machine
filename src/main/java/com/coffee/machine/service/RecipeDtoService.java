package com.coffee.machine.service;

import com.coffee.machine.dto.RecipeDTO;
import com.coffee.machine.model.Recipe;

public interface RecipeDtoService extends RecipeService {
    Recipe create(RecipeDTO recipeDTO);
}
