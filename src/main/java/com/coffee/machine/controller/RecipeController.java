package com.coffee.machine.controller;

import com.coffee.machine.dto.RecipeDTO;
import com.coffee.machine.exception.BadNumberException;
import com.coffee.machine.exception.NotEnoughException;
import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Recipe;
import com.coffee.machine.service.RecipeProductionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Рецепты", description = "Управление рецептами напитков")
public class RecipeController {

    private final RecipeProductionService recipeService;

    @Operation(summary = "Получить все рецепты", description = "Возвращает список всех рецептов")
    @GetMapping
    public List<Recipe> getAll() {
        return recipeService.findAll();
    }

    @Operation(summary = "Получить рецепт по ID",
            description = "Возвращает рецепт по заданному идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Рецепт найден"),
                    @ApiResponse(responseCode = "404", description = "Рецепт не найден")
            })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID рецепта", example = "1")
            @PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @Operation(summary = "Создать новый рецепт",
            description = "Добавляет новый рецепт напитка",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Рецепт создан"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RecipeDTO recipeDTO)
            throws NotFoundException, BadNumberException {
        return ResponseEntity.ok(recipeService.create(recipeDTO));
    }

    @Operation(summary = "Приготовить напиток",
            description = "Использует рецепт для приготовления напитка",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Напиток приготовлен"),
                    @ApiResponse(responseCode = "400", description = "Ошибка количества"),
                    @ApiResponse(responseCode = "404", description = "Рецепт не найден"),
                    @ApiResponse(responseCode = "409", description = "Недостаточно ингредиентов")
            })
    @PostMapping("/{id}/make")
    public ResponseEntity<Map<String, BigDecimal>> makeDrink(
            @Parameter(description = "ID рецепта", example = "1")
            @PathVariable Long id) throws BadNumberException, NotFoundException, NotEnoughException {
        return ResponseEntity.ok(recipeService.makeDrink(id));
    }

    @Operation(summary = "Обновить рецепт",
            description = "Изменяет данные рецепта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Рецепт обновлён"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные"),
                    @ApiResponse(responseCode = "404", description = "Рецепт не найден")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(
            @Parameter(description = "ID рецепта", example = "1")
            @PathVariable Long id,
            @RequestBody Recipe recipe) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(recipeService.update(id, recipe));
    }

    @Operation(summary = "Удалить рецепт",
            description = "Удаляет рецепт по ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Рецепт удалён"),
                    @ApiResponse(responseCode = "404", description = "Рецепт не найден")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID рецепта", example = "1")
            @PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
