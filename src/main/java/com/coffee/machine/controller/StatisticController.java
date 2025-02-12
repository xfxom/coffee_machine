package com.coffee.machine.controller;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.service.BeverageStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/statistic")
@AllArgsConstructor
public class StatisticController {

    private final BeverageStatisticService statisticService;

    @GetMapping("/most-popular")
    public ResponseEntity<?> getMostPopularDrink() throws NotFoundException {
        return ResponseEntity.ok(statisticService.getMostPopularDrinkName());
    }

}
