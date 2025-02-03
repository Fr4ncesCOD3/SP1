package com.example.demo.config; // Definisce il package in cui si trova questa classe

// Importa le classi necessarie da Spring Framework e dal nostro modello
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Drink;
import com.example.demo.model.Menu;
import com.example.demo.model.Pizza;
import com.example.demo.model.Topping;

@Configuration // Indica a Spring che questa è una classe di configurazione
public class MenuConfig {

    // I metodi annotati con @Bean creano oggetti che verranno gestiti da Spring

    @Bean // Crea un topping di prosciutto
    public Topping prosciutto() {
        return new Topping("Prosciutto", 2.0, 35); // Nome, prezzo, calorie
    }

    @Bean // Crea un topping di ananas
    public Topping ananas() {
        return new Topping("Ananas", 1.5, 24);
    }

    @Bean // Crea un topping di funghi
    public Topping funghi() {
        return new Topping("Funghi", 1.8, 20);
    }

    @Bean // Crea un topping di salame piccante
    public Topping salamePiccante() {
        return new Topping("Salame Piccante", 2.0, 40);
    }

    @Bean // Crea un topping di olive
    public Topping olive() {
        return new Topping("Olive", 1.5, 25);
    }

    @Bean // Crea un topping di rucola
    public Topping rucola() {
        return new Topping("Rucola", 1.0, 5);
    }

    @Bean // Crea un topping di gorgonzola
    public Topping gorgonzola() {
        return new Topping("Gorgonzola", 2.0, 90);
    }

    @Bean // Crea una pizza margherita base
    public Pizza margherita() {
        return new Pizza("Margherita", 8.5, 800);
    }

    @Bean // Crea una pizza hawaiian partendo da una margherita e aggiungendo prosciutto e ananas
    public Pizza hawaiian(Pizza margherita, Topping prosciutto, Topping ananas) {
        Pizza hawaiian = new Pizza("Hawaiian", margherita.getPrice(), margherita.getCalories());
        hawaiian.addTopping(prosciutto);
        hawaiian.addTopping(ananas);
        return hawaiian;
    }

    @Bean // Crea una pizza diavola partendo da una margherita e aggiungendo salame piccante
    public Pizza diavola(Pizza margherita, Topping salamePiccante) {
        Pizza diavola = new Pizza("Diavola", margherita.getPrice(), margherita.getCalories());
        diavola.addTopping(salamePiccante);
        return diavola;
    }

    @Bean // Crea una pizza quattro formaggi partendo da una margherita e aggiungendo gorgonzola
    public Pizza quattroFormaggi(Pizza margherita, Topping gorgonzola) {
        Pizza quattroFormaggi = new Pizza("Quattro Formaggi", margherita.getPrice(), margherita.getCalories());
        quattroFormaggi.addTopping(gorgonzola);
        return quattroFormaggi;
    }

    @Bean // Crea una bevanda Coca Cola
    public Drink coca() {
        return new Drink("Coca Cola", 3.0, 140, 330); // Nome, prezzo, calorie, millilitri
    }

    @Bean // Crea una bevanda acqua
    public Drink water() {
        return new Drink("Acqua Naturale", 1.5, 0, 500);
    }

    @Bean // Crea una bevanda birra
    public Drink beer() {
        return new Drink("Birra", 4.0, 150, 400);
    }

    @Bean // Crea una bevanda vino
    public Drink wine() {
        return new Drink("Vino della Casa", 5.0, 85, 200);
    }

    @Bean // Crea una bevanda sprite
    public Drink sprite() {
        return new Drink("Sprite", 3.0, 130, 330);
    }

    @Bean // Crea il menu completo con tutte le pizze, bevande e toppings
    public Menu menu(Pizza margherita, Pizza hawaiian, Pizza diavola, Pizza quattroFormaggi,
                    Drink coca, Drink water, Drink beer, Drink wine, Drink sprite,
                    Topping prosciutto, Topping ananas, Topping funghi, 
                    Topping salamePiccante, Topping olive, Topping rucola, Topping gorgonzola) {
        Menu menu = new Menu(); // Crea un nuovo menu vuoto
        
        // Aggiunge le pizze in formato normale al menu
        menu.addPizza(margherita);
        menu.addPizza(hawaiian);
        menu.addPizza(diavola);
        menu.addPizza(quattroFormaggi);
        
        // Crea e aggiunge le versioni XL delle pizze
        Pizza margheritaXL = new Pizza(margherita.getName(), margherita.getPrice(), margherita.getCalories());
        margheritaXL.makeXL(); // Converte la pizza in formato XL
        menu.addPizza(margheritaXL);
        
        Pizza hawaiianXL = new Pizza(hawaiian.getName(), hawaiian.getPrice(), hawaiian.getCalories());
        hawaiianXL.makeXL();
        menu.addPizza(hawaiianXL);
        
        Pizza diavolaXL = new Pizza(diavola.getName(), diavola.getPrice(), diavola.getCalories());
        diavolaXL.makeXL();
        menu.addPizza(diavolaXL);
        
        // Aggiunge tutte le bevande disponibili al menu
        menu.addDrink(coca);
        menu.addDrink(water);
        menu.addDrink(beer);
        menu.addDrink(wine);
        menu.addDrink(sprite);
        
        // Aggiunge tutti i toppings disponibili al menu
        menu.addTopping(prosciutto);
        menu.addTopping(ananas);
        menu.addTopping(funghi);
        menu.addTopping(salamePiccante);
        menu.addTopping(olive);
        menu.addTopping(rucola);
        menu.addTopping(gorgonzola);
        
        return menu; // Restituisce il menu completo
    }
} 