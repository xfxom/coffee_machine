package com.coffee.machine.controller;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.service.BeverageStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
@AllArgsConstructor
@Tag(name = "Статистика", description = "Статистика по напиткам")
public class StatisticController {

    private final BeverageStatisticService statisticService;

    @Operation(
            summary = "Получить самый популярный напиток",
            description = "Возвращает самый часто заказываемый напиток",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный ответ"),
                    @ApiResponse(responseCode = "404", description = "Статистика не найдена")
            }
    )
    @GetMapping("/most-popular")
    public ResponseEntity<?> getMostPopularDrink() throws NotFoundException {
        return ResponseEntity.ok(statisticService.getMostPopularDrinkName());
    }
}
