package com.bankapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String type; // DEPOSIT / WITHDRAW
    private double amount;
    private LocalDateTime date;

    public Transaction() {}

    public Transaction(String email, String type, double amount) {
        this.email = email;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}