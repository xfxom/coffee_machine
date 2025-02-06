package com.coffee.machine.controller;

import com.coffee.machine.dto.RecipeDTO;
import com.coffee.machine.model.Recipe;
import com.coffee.machine.service.RecipeDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeDtoService recipeService;

    @GetMapping
    public List<Recipe> getAll() {
        return recipeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable Long id) {
        return recipeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Recipe create(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.create(recipeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody Recipe recipe) {
        try {
            Recipe updated = recipeService.update(id, recipe);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
