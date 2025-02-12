package com.coffee.machine.service;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Ingredient;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> findAll();
    Optional<Ingredient> findById(Long id);
    Ingredient create(Ingredient ingredient) throws NotFoundException, BadRequestException;
    Ingredient update(Long id, Ingredient ingredient) throws NotFoundException;
    void delete(Long id);
}