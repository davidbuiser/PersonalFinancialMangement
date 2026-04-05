package org.codefactory.team07.personalfinancialmanagement.domain.model;

public class Expense {

    private String description;
    private double amount;

    public Expense(String description, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
}