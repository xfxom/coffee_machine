package com.coffee.machine.controller;

import com.coffee.machine.dto.RecipeDTO;
import com.coffee.machine.exception.BadNumberException;
import com.coffee.machine.exception.NotEnoughException;
import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Recipe;
import com.coffee.machine.service.RecipeProductionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeProductionService recipeService;

    @GetMapping
    public List<Recipe> getAll() {
        return recipeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RecipeDTO recipeDTO) throws NotFoundException, BadNumberException {
        return ResponseEntity.ok(recipeService.create(recipeDTO));
    }

    @PostMapping("/{id}/make")
    public ResponseEntity<Map<String, BigDecimal>> makeDrink(@PathVariable Long id) throws BadNumberException, NotFoundException, NotEnoughException {
        return ResponseEntity.ok(recipeService.makeDrink(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody Recipe recipe) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(recipeService.update(id, recipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
