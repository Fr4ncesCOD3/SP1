// Questo è il package dove si trova la classe
package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Classe Drink che estende MenuItem - rappresenta una bevanda nel menu
@Entity
@Table(name = "drinks")
public class Drink extends MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Dimensione della bevanda in millilitri
    @Column
    private double size; // in ml

    // Costruttore vuoto richiesto da JPA/Hibernate
    public Drink() {
        super();
    }

    // Costruttore che inizializza una nuova bevanda
    // Parametri:
    // - name: nome della bevanda
    // - price: prezzo della bevanda
    // - calories: calorie della bevanda
    // - size: dimensione in ml della bevanda
    public Drink(String name, double price, int calories, double size) {
        // Chiama il costruttore della classe padre (MenuItem) 
        super(name, price, calories);
        // Inizializza la dimensione della bevanda
        this.size = size;
    }

    // Metodo getter per ottenere la dimensione della bevanda
    public double getSize() { return size; }
    // Metodo setter per modificare la dimensione della bevanda
    public void setSize(double size) { this.size = size; }

    // Override del metodo toString per fornire una rappresentazione testuale della bevanda
    // Aggiunge la dimensione in ml alla rappresentazione base di MenuItem
    @Override
    public String toString() {
        return super.toString() + " (" + size + "ml)";
    }
} 