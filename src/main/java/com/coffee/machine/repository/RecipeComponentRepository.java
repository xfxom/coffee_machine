package com.coffee.machine.repository;

import com.coffee.machine.model.RecipeComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeComponentRepository extends JpaRepository<RecipeComponent, Long> {
}
