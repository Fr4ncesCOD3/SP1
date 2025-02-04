package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Table;

@Configuration
public class TableConfig {
    
    @Bean
    public List<Table> tables() {
        List<Table> tables = new ArrayList<>();
        // Inizializza alcuni tavoli con diverse capacità
        tables.add(new Table(1, 2));  // Tavolo per due persone
        tables.add(new Table(2, 4));  // Tavolo per quattro persone
        tables.add(new Table(3, 6));  // Tavolo per sei persone
        tables.add(new Table(4, 8));  // Tavolo per otto persone
        return tables;
    }
} 