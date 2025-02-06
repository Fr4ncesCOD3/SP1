// Questo è il package dove si trova la classe
package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// Classe astratta MenuItem - è la classe base per tutti gli elementi del menu (pizze, bevande, ecc)
// È astratta perché non creeremo mai un MenuItem direttamente, ma solo oggetti delle sue sottoclassi
@MappedSuperclass
public abstract class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Attributi privati che ogni elemento del menu deve avere
    private String name;    // Nome dell'elemento (es: "Margherita", "Coca Cola")
    private double price;   // Prezzo in euro
    private int calories;   // Contenuto calorico

    // Costruttore vuoto richiesto da JPA/Hibernate
    protected MenuItem() {}

    // Costruttore: viene chiamato quando creiamo un nuovo elemento del menu
    // Richiede nome, prezzo e calorie come parametri
    public MenuItem(String name, double price, int calories) {
        this.name = name;       // Inizializza il nome
        this.price = price;     // Inizializza il prezzo
        this.calories = calories; // Inizializza le calorie
    }

    // Metodi getter e setter per accedere e modificare gli attributi privati
    // I getter permettono di leggere i valori
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    // Override del metodo toString per convertire l'oggetto in una stringa leggibile
    // Esempio output: "Margherita - €8.50 (cal: 800)"
    @Override
    public String toString() {
        return name + " - €" + price + " (cal: " + calories + ")";
    }

    // Aggiungi getter e setter per id
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
} 