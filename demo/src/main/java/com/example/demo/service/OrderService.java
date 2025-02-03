package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.example.demo.model.Drink;
import com.example.demo.model.Menu;
import com.example.demo.model.MenuItem;
import com.example.demo.model.Pizza;
import com.example.demo.model.Topping;

@Service
public class OrderService {
    private final Menu menu;
    private final Scanner scanner;

    public OrderService(Menu menu) {
        this.menu = menu;
        this.scanner = new Scanner(System.in);
    }

    public void startOrder() {
        List<MenuItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        System.out.println(menu);
        
        while (true) {
            System.out.println("\nInserisci il tuo ordine (o 'fine' per terminare):");
            System.out.println("Formato: 'Nome Pizza + [Extra/Bevanda1] + [Extra/Bevanda2]...'");
            System.out.println("Esempio: 'Margherita + Coca Cola + Olive'");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("fine")) {
                break;
            }

            String[] parts = input.split("\\+");
            if (parts.length == 0) continue;

            String pizzaName = parts[0].trim();
            
            // Cerca la pizza base
            Pizza selectedPizza = menu.getPizzas().stream()
                    .filter(p -> p.getName().equalsIgnoreCase(pizzaName))
                    .findFirst()
                    .orElse(null);

            if (selectedPizza == null) {
                System.out.println("Pizza non trovata! Riprova.");
                continue;
            }

            // Crea una nuova pizza per l'ordine
            Pizza orderedPizza = new Pizza(selectedPizza.getName(), selectedPizza.getPrice(), selectedPizza.getCalories());
            for (Topping t : selectedPizza.getToppings()) {
                orderedPizza.addTopping(t);
            }

            // Processa gli extra e le bevande
            if (parts.length > 1) {
                for (int i = 1; i < parts.length; i++) {
                    String itemName = parts[i].trim();
                    
                    // Cerca tra le bevande
                    Drink drink = menu.getDrinks().stream()
                            .filter(d -> d.getName().equalsIgnoreCase(itemName))
                            .findFirst()
                            .orElse(null);

                    if (drink != null) {
                        orderItems.add(drink);
                        totalPrice += drink.getPrice();
                        System.out.println("Aggiunta bevanda: " + drink);
                        continue;
                    }

                    // Se non è una bevanda, cerca tra i topping
                    Topping topping = menu.getAvailableToppings().stream()
                            .filter(t -> t.getName().equalsIgnoreCase(itemName))
                            .findFirst()
                            .orElse(null);

                    if (topping != null) {
                        orderedPizza.addTopping(topping);
                        System.out.println("Aggiunto topping: " + topping.getName());
                    } else {
                        System.out.println("Item '" + itemName + "' non trovato nel menu!");
                    }
                }
            }

            orderItems.add(orderedPizza);
            totalPrice += orderedPizza.getPrice();
            System.out.println("Aggiunta pizza: " + orderedPizza);
        }

        // Stampa riepilogo ordine
        if (!orderItems.isEmpty()) {
            System.out.println("\n=== RIEPILOGO ORDINE ===");
            System.out.println("\nPIZZE E BEVANDE ORDINATE:");
            orderItems.forEach(item -> System.out.println("- " + item));
            System.out.printf("\nTotale ordine: €%.2f%n", totalPrice);
        } else {
            System.out.println("\nNessun item ordinato.");
        }
    }
} 