package com.coffee.machine.service.impl;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.BeverageHistory;
import com.coffee.machine.model.Recipe;
import com.coffee.machine.repository.BeverageHistoryRepository;
import com.coffee.machine.service.BeverageStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BeverageStatisticServiceImpl implements BeverageStatisticService {

    private final BeverageHistoryRepository beverageHistoryRepository;

    @Override
    public void addToHistory(Recipe recipe) {
        BeverageHistory beverageHistory = new BeverageHistory();
        beverageHistory.setDrink(recipe);
        beverageHistoryRepository.save(beverageHistory);
    }

    @Override
    public Recipe getMostPopularDrinkName() throws NotFoundException {
        return beverageHistoryRepository.findMostPopularRecipe()
                .orElseThrow(() -> new NotFoundException("Most popular drink not found"));
    }
}
