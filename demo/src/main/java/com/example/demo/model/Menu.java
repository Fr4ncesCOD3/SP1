// Package che contiene questa classe
package com.example.demo.model;

// Importiamo le classi necessarie per gestire le liste
import java.util.ArrayList;
import java.util.List;

// Classe Menu che rappresenta il menu della pizzeria
public class Menu {
    // Liste per memorizzare i diversi tipi di prodotti disponibili
    private List<Pizza> pizzas;              // Lista delle pizze
    private List<Drink> drinks;              // Lista delle bevande
    private List<Topping> availableToppings; // Lista dei condimenti disponibili

    // Costruttore: viene chiamato quando creiamo un nuovo Menu
    public Menu() {
        // Inizializziamo le liste vuote
        this.pizzas = new ArrayList<>();
        this.drinks = new ArrayList<>();
        this.availableToppings = new ArrayList<>();
    }

    // Metodi per aggiungere nuovi prodotti alle liste
    public void addPizza(Pizza pizza) { pizzas.add(pizza); }         // Aggiunge una pizza al menu
    public void addDrink(Drink drink) { drinks.add(drink); }         // Aggiunge una bevanda al menu
    public void addTopping(Topping topping) { availableToppings.add(topping); } // Aggiunge un condimento al menu

    // Metodi per ottenere le liste dei prodotti (getter)
    public List<Pizza> getPizzas() { return pizzas; }                // Restituisce la lista delle pizze
    public List<Drink> getDrinks() { return drinks; }                // Restituisce la lista delle bevande
    public List<Topping> getAvailableToppings() { return availableToppings; } // Restituisce la lista dei condimenti

    // Metodo che converte il menu in una stringa leggibile
    @Override
    public String toString() {
        // StringBuilder ci aiuta a costruire una stringa in modo efficiente
        StringBuilder sb = new StringBuilder();
        // Aggiungiamo il titolo del menu
        sb.append("=== MENU PIZZERIA ===\n\n");
        
        // Sezione pizze: lista tutte le pizze disponibili
        sb.append("PIZZE:\n");
        pizzas.forEach(p -> sb.append("- ").append(p).append("\n")); // Per ogni pizza, aggiunge una riga
        
        // Sezione bevande: lista tutte le bevande disponibili
        sb.append("\nBEVANDE:\n");
        drinks.forEach(d -> sb.append("- ").append(d).append("\n")); // Per ogni bevanda, aggiunge una riga
        
        // Sezione condimenti: lista tutti i condimenti disponibili
        sb.append("\nTOPPING DISPONIBILI:\n");
        availableToppings.forEach(t -> sb.append("- ").append(t).append("\n")); // Per ogni condimento, aggiunge una riga
        
        // Restituisce la stringa completa del menu
        return sb.toString();
    }
} 