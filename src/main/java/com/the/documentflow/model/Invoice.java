package com.the.documentflow.model;


import java.time.LocalDate;

public class Invoice extends Document {
    private String currency;
    private double exchangeRate;
    private String product;
    private double quantity;

    public Invoice(String number, LocalDate date, String user, double amount,
                   String currency, double exchangeRate, String product, double quantity) {
        super(number, date, user, amount);
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.product = product;
        this.quantity = quantity;
    }

    // Геттеры и сеттеры
    @Override
    public String getType() { return "Накладная"; }
    public String getCurrency() { return currency; }
    public double getExchangeRate() { return exchangeRate; }
    public String getProduct() { return product; }
    public double getQuantity() { return quantity; }

    @Override
    public String getDescription() {
        return String.format("Накладная №%s от %s, Сумма: %.2f %s",
                number, date.toString(), amount, currency);
    }
}