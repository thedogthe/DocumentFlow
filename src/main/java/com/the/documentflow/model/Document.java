package com.the.documentflow.model;


import java.time.LocalDate;

public abstract class Document {
    protected String number;
    protected LocalDate date;
    protected String user;
    protected double amount;

    public Document(String number, LocalDate date, String user, double amount) {
        this.number = number;
        this.date = date;
        this.user = user;
        this.amount = amount;
    }

    // Геттеры и сеттеры
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public abstract String getType();
    public abstract String getDescription();
}