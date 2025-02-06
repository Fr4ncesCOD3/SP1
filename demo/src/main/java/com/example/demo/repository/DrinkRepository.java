package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Drink;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    // Query derivata per trovare bevande sotto un certo prezzo
    List<Drink> findByPriceLessThan(double price);
} 