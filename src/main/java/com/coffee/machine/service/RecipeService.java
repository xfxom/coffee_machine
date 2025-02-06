package com.coffee.machine.service;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<Recipe> findAll();
    Optional<Recipe> findById(Long id) throws NotFoundException;
    Recipe create(Recipe recipe);
    Recipe update(Long id, Recipe recipe) throws NotFoundException;
    void delete(Long id);
}