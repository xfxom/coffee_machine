package com.coffee.machine.controller;

import com.coffee.machine.dto.RecipeDTO;
import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Recipe;
import com.coffee.machine.service.RecipeDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RecipeDTO recipeDTO) throws NotFoundException {
        return ResponseEntity.ok(recipeService.create(recipeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody Recipe recipe) throws NotFoundException {
        return ResponseEntity.ok(recipeService.update(id, recipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
