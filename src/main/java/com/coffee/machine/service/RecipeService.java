package com.coffee.machine.service;

import com.coffee.machine.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<Recipe> findAll();
    Optional<Recipe> findById(Long id);
    Recipe create(Recipe recipe);
    Recipe update(Long id, Recipe recipe);
    void delete(Long id);
}