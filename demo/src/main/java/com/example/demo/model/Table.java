package com.example.demo.model;

// Classe che rappresenta un tavolo del ristorante
public class Table {
    private int number;           // Numero del tavolo
    private int maxSeats;         // Numero massimo di coperti
    private TableStatus status;   // Stato del tavolo

    // Costruttore
    public Table(int number, int maxSeats) {
        this.number = number;
        this.maxSeats = maxSeats;
        this.status = TableStatus.FREE;  // All'inizio il tavolo è libero
    }

    // Getters e setters
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public int getMaxSeats() { return maxSeats; }
    public void setMaxSeats(int maxSeats) { this.maxSeats = maxSeats; }
    public TableStatus getStatus() { return status; }
    public void setStatus(TableStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Tavolo " + number + " (max " + maxSeats + " posti, " + status + ")";
    }
} 