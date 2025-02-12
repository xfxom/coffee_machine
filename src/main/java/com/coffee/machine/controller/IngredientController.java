package com.coffee.machine.controller;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Ingredient;
import com.coffee.machine.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@Tag(name = "Ингредиенты", description = "Управление ингредиентами кофемашины")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    @Operation(summary = "Получить все ингредиенты", description = "Возвращает список всех доступных ингредиентов")
    @ApiResponse(responseCode = "200", description = "Успешное получение списка ингредиентов")
    public List<Ingredient> getAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ингредиент по ID", description = "Возвращает ингредиент по указанному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ингредиент найден"),
            @ApiResponse(responseCode = "404", description = "Ингредиент не найден")
    })
    public ResponseEntity<?> getById(
            @Parameter(description = "ID ингредиента", required = true, example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ingredientService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новый ингредиент", description = "Добавляет новый ингредиент в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ингредиент успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Связанный ресурс не найден")
    })
    public Ingredient create(@RequestBody Ingredient ingredient) throws NotFoundException, BadRequestException {
        return ingredientService.create(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить ингредиент", description = "Обновляет данные существующего ингредиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ингредиент успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Ингредиент не найден")
    })
    public ResponseEntity<Ingredient> update(
            @Parameter(description = "ID ингредиента для обновления", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody Ingredient ingredient
    ) throws NotFoundException {
        return ResponseEntity.ok(ingredientService.update(id, ingredient));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ингредиент", description = "Удаляет ингредиент из системы по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ингредиент успешно удален"),
            @ApiResponse(responseCode = "404", description = "Ингредиент не найден")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID ингредиента для удаления", required = true, example = "1")
            @PathVariable Long id
    ) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}