package com.coffee.machine.service;

import com.coffee.machine.exception.BadNumberException;
import com.coffee.machine.exception.NotEnoughException;
import com.coffee.machine.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.Map;

public interface RecipeProductionService extends RecipeDtoService {
    Map<String, BigDecimal> makeDrink(Long recipeId) throws NotFoundException, BadNumberException, NotEnoughException;
}
