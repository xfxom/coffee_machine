package com.coffee.machine.repository;

import com.coffee.machine.model.BeverageHistory;
import com.coffee.machine.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeverageHistoryRepository extends JpaRepository<BeverageHistory, Long> {

    /**
     * Найти самый популярный напиток за всё время.
     * @return Рецепт напитка
     */
    @Query("""
        SELECT r 
        FROM BeverageHistory bh
        JOIN bh.drink r
        GROUP BY r
        ORDER BY COUNT(bh) DESC
        LIMIT 1
    """)
    Optional<Recipe> findMostPopularRecipe();

}