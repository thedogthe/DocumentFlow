package com.the.documentflow.model;

import java.time.LocalDate;

public class PaymentRequest extends Document {
    private String counterparty;
    private String currency;
    private double exchangeRate;
    private double commission;

    public PaymentRequest(String number, LocalDate date, String user, double amount,
                         String counterparty, String currency, double exchangeRate, double commission) {
        super(number, date, user, amount);
        this.counterparty = counterparty;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.commission = commission;
    }

    // Геттеры и сеттеры
    @Override
    public String getType() { return "Заявка на оплату"; }
    public String getCounterparty() { return counterparty; }
    public String getCurrency() { return currency; }
    public double getExchangeRate() { return exchangeRate; }
    public double getCommission() { return commission; }

    @Override
    public String getDescription() {
        return String.format("Заявка на оплату №%s от %s, Сумма: %.2f %s",
                number, date.toString(), amount, currency);
    }
}