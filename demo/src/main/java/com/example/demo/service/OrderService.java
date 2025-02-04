package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.model.Drink;
import com.example.demo.model.Menu;
import com.example.demo.model.MenuItem;
import com.example.demo.model.Order;
import com.example.demo.model.Pizza;
import com.example.demo.model.Table;
import com.example.demo.model.TableStatus;
import com.example.demo.model.Topping;

@Service
public class OrderService {
    private final Menu menu;
    private final Scanner scanner;
    private final List<Table> tables;
    private final double coverCharge;

    public OrderService(Menu menu, List<Table> tables, @Value("${restaurant.cover.charge}") double coverCharge) {
        this.menu = menu;
        this.scanner = new Scanner(System.in);
        this.tables = tables;
        this.coverCharge = coverCharge;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== SISTEMA ORDINI RISTORANTE ===");
            System.out.println("1. Ordine al tavolo");
            System.out.println("2. Ordine delivery");
            System.out.println("3. Esci");
            System.out.print("Seleziona un'opzione: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    handleTableOrder();
                    break;
                case "2":
                    handleDeliveryOrder();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opzione non valida!");
            }
        }
    }

    private void handleTableOrder() {
        // Mostra stato dei tavoli
        System.out.println("\n=== STATO DEI TAVOLI ===");
        System.out.println("Tavoli liberi:");
        tables.stream()
              .filter(t -> t.getStatus() == TableStatus.FREE)
              .forEach(System.out::println);
              
        System.out.println("\nTavoli occupati:");
        tables.stream()
              .filter(t -> t.getStatus() == TableStatus.OCCUPIED)
              .forEach(System.out::println);

        // Selezione tavolo
        System.out.print("\nInserisci il numero del tavolo (0 per annullare): ");
        int tableNumber = Integer.parseInt(scanner.nextLine());
        
        if (tableNumber == 0) return;

        Table selectedTable = tables.stream()
                                  .filter(t -> t.getNumber() == tableNumber && t.getStatus() == TableStatus.FREE)
                                  .findFirst()
                                  .orElse(null);

        if (selectedTable == null) {
            System.out.println("Tavolo non disponibile!");
            return;
        }

        System.out.print("Inserisci il numero di coperti: ");
        int covers = Integer.parseInt(scanner.nextLine());

        if (covers > selectedTable.getMaxSeats()) {
            System.out.println("Numero di coperti superiore alla capacità del tavolo!");
            return;
        }

        Order order = new Order(selectedTable, covers, coverCharge);
        processOrder(order);
    }

    private void handleDeliveryOrder() {
        Order order = new Order(null, 0, 0); // Per delivery non ci sono coperti
        processOrder(order);
    }

    private void processOrder(Order order) {
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

        // Aggiungi tutti gli items all'ordine
        orderItems.forEach(order::addItem);

        // Stampa riepilogo ordine
        System.out.println("\n" + order.toString());
    }
} 