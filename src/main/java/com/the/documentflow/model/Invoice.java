package com.the.documentflow.model;

import java.time.LocalDate;

/**
 * Класс, представляющий накладную в системе документооборота.
 * Наследуется от абстрактного класса {@link Document}.
 */
public class Invoice extends Document {
    /** Валюта накладной */
    private String currency;

    /** Курс обмена валюты */
    private double exchangeRate;

    /** Наименование товара */
    private String product;

    /** Количество товара */
    private double quantity;

    /**
     * Создает новый экземпляр накладной.
     *
     * @param number номер документа
     * @param date дата создания
     * @param user пользователь-создатель
     * @param amount сумма
     * @param currency валюта (например, "RUB", "USD")
     * @param exchangeRate курс обмена
     * @param product наименование товара
     * @param quantity количество товара
     */
    public Invoice(String number, LocalDate date, String user, double amount,
                   String currency, double exchangeRate, String product, double quantity) {
        super(number, date, user, amount);
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Возвращает тип документа ("Накладная").
     *
     * @return строку с типом документа
     */
    @Override
    public String getType() {
        return "Накладная";
    }

    /**
     * Возвращает валюту накладной.
     *
     * @return строку с валютой
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Возвращает курс обмена валюты.
     *
     * @return курс обмена
     */
    public double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Возвращает наименование товара.
     *
     * @return строку с наименованием товара
     */
    public String getProduct() {
        return product;
    }

    /**
     * Возвращает количество товара.
     *
     * @return количество
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Возвращает описание накладной в формате:
     * "Накладная №[номер] от [дата], Сумма: [сумма] [валюта]".
     *
     * @return строку с описанием накладной
     */
    @Override
    public String getDescription() {
        return String.format("Накладная №%s от %s, Сумма: %.2f %s",
                number, date.toString(), amount, currency);
    }
}