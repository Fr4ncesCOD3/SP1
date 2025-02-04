package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Classe che rappresenta un ordine
public class Order {
    private static int orderCounter = 0;  // Contatore statico per generare numeri ordine univoci
    
    private int orderNumber;              // Numero dell'ordine
    private Table table;                  // Tavolo associato all'ordine
    private int numberOfCovers;           // Numero di coperti
    private List<MenuItem> items;         // Items ordinati
    private OrderStatus status;           // Stato dell'ordine
    private LocalDateTime orderTime;      // Ora di acquisizione dell'ordine
    private double coverCharge;           // Costo del coperto
    private OrderType type;
    
    // Costruttore
    public Order(Table table, int numberOfCovers, double coverCharge) {
        this.orderNumber = ++orderCounter;
        this.table = table;
        this.numberOfCovers = numberOfCovers;
        this.items = new ArrayList<>();
        this.status = OrderStatus.IN_PROGRESS;
        this.orderTime = LocalDateTime.now();
        this.coverCharge = coverCharge;
        this.type = (table != null) ? OrderType.TABLE : OrderType.DELIVERY;
        
        // Imposta il tavolo come occupato
        if (table != null) {
            table.setStatus(TableStatus.OCCUPIED);
        }
    }
    
    // Metodo per aggiungere un item all'ordine
    public void addItem(MenuItem item) {
        items.add(item);
    }
    
    // Calcola il totale dell'ordine
    public double getTotal() {
        double itemsTotal = items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
        return itemsTotal + (numberOfCovers * coverCharge);
    }
    
    // Getters e setters
    public int getOrderNumber() { return orderNumber; }
    public Table getTable() { return table; }
    public int getNumberOfCovers() { return numberOfCovers; }
    public List<MenuItem> getItems() { return items; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getOrderTime() { return orderTime; }
    public OrderType getType() { return type; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ORDINE #").append(orderNumber).append(" ===\n");
        sb.append("Tipo: ").append(type).append("\n");
        
        if (type == OrderType.TABLE) {
            sb.append("Tavolo: ").append(table.getNumber()).append("\n");
            sb.append("Coperti: ").append(numberOfCovers).append("\n");
            sb.append("Stato: ").append(status).append("\n");
        }
        
        sb.append("Ora: ").append(orderTime).append("\n");
        sb.append("\nItems ordinati:\n");
        items.forEach(item -> sb.append("- ").append(item).append("\n"));
        
        if (type == OrderType.TABLE) {
            sb.append("\nTotale coperti: €").append(String.format("%.2f", numberOfCovers * coverCharge));
        }
        
        sb.append("\nTotale ordine: €").append(String.format("%.2f", getTotal()));
        return sb.toString();
    }
} 