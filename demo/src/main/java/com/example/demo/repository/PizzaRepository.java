package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.model.Pizza;
import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    // Query derivata
    List<Pizza> findByPriceLessThan(double price);
    
    // Query custom
    @Query("SELECT p FROM Pizza p WHERE p.calories < :maxCalories AND p.isXL = false")
    List<Pizza> findLightPizzas(int maxCalories);
} 