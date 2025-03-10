// Package che contiene questa classe
package com.example.demo.model;

// Importiamo le classi necessarie per gestire le liste
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

// Classe Pizza che estende MenuItem - rappresenta una pizza nel menu
@Entity
@Table(name = "pizzas")
public class Pizza extends MenuItem {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "pizza_toppings",
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn(name = "topping_id")
    )
    private List<Topping> toppings = new ArrayList<>();
    // Flag che indica se la pizza è formato XL
    @Column
    private boolean isXL;
    // Moltiplicatori per calcolare prezzo e calorie della versione XL
    private static final double XL_PRICE_MULTIPLIER = 1.5;    // Prezzo aumentato del 50%
    private static final double XL_CALORIES_MULTIPLIER = 1.8; // Calorie aumentate dell'80%

    // Costruttore vuoto richiesto da JPA/Hibernate
    public Pizza() {
        super();  // Chiama il costruttore vuoto della classe padre
    }

    // Costruttore: crea una nuova pizza con nome, prezzo e calorie base
    public Pizza(String name, double price, int calories) {
        // Chiama il costruttore della classe padre (MenuItem)
        super(name, price, calories);
        // Inizialmente la pizza non è XL
        this.isXL = false;
    }

    // Metodo per aggiungere un condimento alla pizza
    public void addTopping(Topping topping) {
        // Aggiunge il condimento alla lista
        toppings.add(topping);
        // Aggiorna il prezzo totale aggiungendo il costo del condimento
        setPrice(getPrice() + topping.getPrice());
        // Aggiorna le calorie totali aggiungendo quelle del condimento
        setCalories(getCalories() + topping.getCalories());
    }

    // Metodo per convertire la pizza in formato XL
    public void makeXL() {
        // Controlla se la pizza non è già XL
        if (!isXL) {
            // Aumenta il prezzo moltiplicandolo per 1.5
            setPrice(getPrice() * XL_PRICE_MULTIPLIER);
            // Aumenta le calorie moltiplicandole per 1.8
            setCalories((int)(getCalories() * XL_CALORIES_MULTIPLIER));
            // Aggiunge "XL" al nome della pizza
            setName(getName() + " XL");
            // Segna la pizza come XL
            isXL = true;
        }
    }

    // Metodo per ottenere la lista dei condimenti
    public List<Topping> getToppings() {
        return toppings;
    }

    // Override del metodo toString per fornire una rappresentazione testuale della pizza
    @Override
    public String toString() {
        // Crea una stringa partendo dalla rappresentazione base di MenuItem
        StringBuilder sb = new StringBuilder(super.toString());
        // Se ci sono condimenti extra, li aggiunge alla descrizione
        if (!toppings.isEmpty()) {
            sb.append(" [extra: ");
            // Aggiunge il nome di ogni condimento separato da virgola
            toppings.forEach(t -> sb.append(t.getName()).append(", "));
            // Rimuove l'ultima virgola e spazio
            sb.setLength(sb.length() - 2);
            sb.append("]");
        }
        return sb.toString();
    }
} 