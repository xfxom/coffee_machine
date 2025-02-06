package com.coffee.machine.service;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> findAll();
    Optional<Ingredient> findById(Long id);
    Ingredient create(Ingredient ingredient) throws NotFoundException;
    Ingredient update(Long id, Ingredient ingredient);
    void delete(Long id);
}