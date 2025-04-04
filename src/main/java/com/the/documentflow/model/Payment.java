package com.the.documentflow.model;



import java.time.LocalDate;

public class Payment extends Document {
    private String employee;

    public Payment(String number, LocalDate date, String user, double amount, String employee) {
        super(number, date, user, amount);
        this.employee = employee;
    }

    // Геттеры и сеттеры
    @Override
    public String getType() { return "Платёжка"; }
    public String getEmployee() { return employee; }

    @Override
    public String getDescription() {
        return String.format("Платёжка №%s от %s, Сумма: %.2f",
                number, date.toString(), amount);
    }
}