package com.coffee.machine.service.impl;

import com.coffee.machine.dto.RecipeComponentDTO;
import com.coffee.machine.dto.RecipeDTO;
import com.coffee.machine.exception.BadNumberException;
import com.coffee.machine.exception.NotEnoughException;
import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Ingredient;
import com.coffee.machine.model.Recipe;
import com.coffee.machine.model.RecipeComponent;
import com.coffee.machine.repository.RecipeRepository;
import com.coffee.machine.service.IngredientService;
import com.coffee.machine.service.RecipeProductionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeProductionService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;

    @Override
    public List<Recipe> findAll() {
        log.info("Fetching all recipes");
        return recipeRepository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        log.info("Fetching recipe by id: {}", id);
        return recipeRepository.findById(id);
    }

    @Override
    public Recipe create(Recipe recipe) {
        log.info("Creating new recipe: {}", recipe.getName());
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe update(Long id, Recipe updatedRecipe) throws NotFoundException, BadRequestException {

        log.info("Updating recipe with id: {}", id);

        if (updatedRecipe == null)
            throw new BadRequestException();

        if (findById(id).isEmpty())
            throw new NotFoundException("Recipe not found");

        Set<Long> visited = new HashSet<>();
        if (detectCycle(updatedRecipe, visited)) {
            throw new BadRequestException("Cycle detected in recipe components");
        }

        Recipe recipe = new Recipe();

        if (updatedRecipe.getName() != null) {
            if (!updatedRecipe.getName().isEmpty() && !updatedRecipe.getComponents().isEmpty()) {
                recipe.setId(id);
                recipe.setName(updatedRecipe.getName());
                recipe.setComponents(updatedRecipe.getComponents());
            }
        }

        return recipeRepository.save(recipe);
    }


    private boolean detectCycle(Recipe recipe, Set<Long> visited) {

        if (recipe.getId() != null && visited.contains(recipe.getId())) {
            return true;
        }

        if (recipe.getId() != null) {
            visited.add(recipe.getId());
        }

        if (recipe.getComponents() != null) {
            for (RecipeComponent comp : recipe.getComponents()) {
                if (comp.getSubRecipe() != null) {
                    if (detectCycle(comp.getSubRecipe(), visited)) {
                        return true;
                    }
                }
            }
        }

        if (recipe.getId() != null) {
            visited.remove(recipe.getId());
        }

        return false;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting recipe with id: {}", id);
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe create(RecipeDTO recipeDTO) throws NotFoundException, BadNumberException {
        log.info("Creating recipe from DTO: {}", recipeDTO.getName());

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

            if (recipeComponentDTO.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadNumberException("Quantity must be greater than 0");
            }

            component.setRecipe(recipe);
            recipeComponents.add(component);
        }
        recipe.setComponents(recipeComponents);

        return create(recipe);
    }

    @Override
    @Transactional
    public Map<String, BigDecimal> makeDrink(Long recipeId) throws NotFoundException, NotEnoughException {

        Recipe recipe = findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found: " + recipeId));

        Map<Ingredient, BigDecimal> required = new HashMap<>();
        computeRequiredIngredients(recipe, BigDecimal.ONE, required);

        for (Map.Entry<Ingredient, BigDecimal> entry : required.entrySet()) {
            Ingredient ing = entry.getKey();
            BigDecimal req = entry.getValue();
            if (BigDecimal.valueOf(ing.getQuantity()).compareTo(req) < 0) {
                throw new NotEnoughException("Not enough ingredient " + ing.getName() +
                        ": need " + req + ", available " + ing.getQuantity());
            }
        }

        for (Map.Entry<Ingredient, BigDecimal> entry : required.entrySet()) {
            Ingredient ing = entry.getKey();
            BigDecimal req = entry.getValue();
            ing.setQuantity(ing.getQuantity() - req.intValue());
            ingredientService.update(entry.getKey().getId(), ing);
        }

        return required.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue));
    }

    private void computeRequiredIngredients(Recipe recipe, BigDecimal multiplier, Map<Ingredient, BigDecimal> result) {

        if (recipe.getComponents() == null) return;

        for (RecipeComponent comp : recipe.getComponents()) {

            BigDecimal total = comp.getQuantity().multiply(multiplier);

            if (comp.getIngredient() != null) {
                result.merge(comp.getIngredient(), total, BigDecimal::add);
            } else if (comp.getSubRecipe() != null) {
                computeRequiredIngredients(comp.getSubRecipe(), total, result);
            }

        }
    }
}