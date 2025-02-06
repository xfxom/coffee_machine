package com.coffee.machine.service.impl;

import com.coffee.machine.dto.RecipeComponentDTO;
import com.coffee.machine.dto.RecipeDTO;
import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Ingredient;
import com.coffee.machine.model.Recipe;
import com.coffee.machine.model.RecipeComponent;
import com.coffee.machine.repository.RecipeRepository;
import com.coffee.machine.service.IngredientService;
import com.coffee.machine.service.RecipeDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeDtoService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;

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
    public Recipe update(Long id, Recipe updatedRecipe) throws NotFoundException {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setName(updatedRecipe.getName());
                    recipe.setComponents(updatedRecipe.getComponents());
                    return recipeRepository.save(recipe);
                })
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe create(RecipeDTO recipeDTO) throws NotFoundException {

        Recipe recipe = new Recipe();

        recipe.setName(recipeDTO.getName());
        List<RecipeComponentDTO> components = recipeDTO.getComponents();
        List<RecipeComponent> recipeComponents = new ArrayList<>();

        for (RecipeComponentDTO recipeComponentDTO : components) {

            RecipeComponent component = new RecipeComponent();
            component.setQuantity(recipeComponentDTO.getQuantity());

            if (recipeComponentDTO.getIngredientId() != null) {
                Ingredient ingredient = ingredientService.findById(recipeComponentDTO.getIngredientId())
                        .orElseThrow(() -> new NotFoundException("Ingredient not found: " + recipeComponentDTO.getIngredientId()));
                component.setIngredient(ingredient);
            }

            if (recipeComponentDTO.getSubRecipeId() != null) {
                Recipe subRecipe = findById(recipeComponentDTO.getSubRecipeId())
                        .orElseThrow(() -> new NotFoundException("Sub-recipe not found: " + recipeComponentDTO.getSubRecipeId()));
                component.setSubRecipe(subRecipe);
            }

            component.setRecipe(recipe);
            recipeComponents.add(component);
        }
        recipe.setComponents(recipeComponents);

        return create(recipe);
    }
}
