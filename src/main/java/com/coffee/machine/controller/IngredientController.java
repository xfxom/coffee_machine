package com.coffee.machine.controller;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Ingredient;
import com.coffee.machine.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public List<Ingredient> getAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.findById(id));
    }

    @PostMapping
    public Ingredient create(@RequestBody Ingredient ingredient) throws NotFoundException {
        return ingredientService.create(ingredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> update(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.update(id, ingredient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
