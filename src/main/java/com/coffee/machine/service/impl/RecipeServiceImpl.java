package com.coffee.machine.service.impl;

import com.coffee.machine.model.Recipe;
import com.coffee.machine.repository.RecipeRepository;
import com.coffee.machine.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Recipe create(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe update(Long id, Recipe updatedRecipe) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setName(updatedRecipe.getName());
                    recipe.setComponents(updatedRecipe.getComponents());
                    return recipeRepository.save(recipe);
                })
                .orElseThrow(() -> new RuntimeException("Рецепт не найден"));
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
}
