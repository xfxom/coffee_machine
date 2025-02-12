package com.coffee.machine.service;

import com.coffee.machine.exception.NotFoundException;
import com.coffee.machine.model.Recipe;

public interface BeverageStatisticService {
    void addToHistory(Recipe recipe);
    Recipe getMostPopularDrinkName() throws NotFoundException;
}
