// Questo è il package dove si trova la classe
package com.example.demo.model;

// Classe Topping che estende MenuItem - rappresenta un condimento per le pizze
// Esempi di condimenti: prosciutto, funghi, olive, ecc.
public class Topping extends MenuItem {
    // Costruttore che crea un nuovo condimento
    // Parametri:
    // - name: nome del condimento (es: "Prosciutto")
    // - price: prezzo aggiuntivo del condimento (es: 2.0 euro)
    // - calories: calorie del condimento (es: 35 kcal)
    public Topping(String name, double price, int calories) {
        // Chiama il costruttore della classe padre (MenuItem)
        // per inizializzare nome, prezzo e calorie
        super(name, price, calories);
    }
} 