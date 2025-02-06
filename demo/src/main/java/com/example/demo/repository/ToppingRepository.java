package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Topping;

public interface ToppingRepository extends JpaRepository<Topping, Long> {
    // Query derivata per trovare condimenti sotto un certo prezzo
    List<Topping> findByPriceLessThan(double price);
} 