package com.coffee.machine.service.impl;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Ingredient;
import com.coffee.machine.repository.IngredientRepository;
import com.coffee.machine.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> findAll() {
        log.info("Get all ingredients");
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        log.info("Get ingredient by id: {}", id);
        return ingredientRepository.findById(id);
    }

    @Override
    public Ingredient create(Ingredient ingredient) throws NotFoundException, BadRequestException {
        log.info("Create ingredient with name: {}", ingredient.getName());

        if (ingredient.getName() == null || ingredient.getName().isEmpty())
            throw new BadRequestException();

        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient update(Long id, Ingredient updatedIngredient) throws NotFoundException {
        log.info("Update ingredient with id: {}", id);

        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient not found"));

        ingredient.setName(updatedIngredient.getName());
        ingredient.setQuantity(updatedIngredient.getQuantity());

        return ingredientRepository.save(ingredient);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete ingredient by id: {}", id);
        ingredientRepository.deleteById(id);
    }
}