package com.the.documentflow.model;

import java.time.LocalDate;

/**
 * Класс, представляющий заявку на оплату в системе документооборота.
 * Наследует базовые свойства от {@link Document} и добавляет специфичные для платежных поручений поля.
 */
public class PaymentRequest extends Document {
    /** Контрагент (получатель платежа) */
    private String counterparty;

    /** Валюта платежа */
    private String currency;

    /** Курс обмена валюты */
    private double exchangeRate;

    /** Комиссия за перевод */
    private double commission;

    /**
     * Создает новую заявку на оплату.
     *
     * @param number уникальный номер документа
     * @param date дата создания документа
     * @param user пользователь, создавший документ
     * @param amount сумма платежа
     * @param counterparty контрагент (получатель платежа)
     * @param currency валюта платежа (например, "RUB", "USD", "EUR")
     * @param exchangeRate курс обмена валюты
     * @param commission комиссия за перевод (в абсолютных единицах)
     */
    public PaymentRequest(String number, LocalDate date, String user, double amount,
                          String counterparty, String currency, double exchangeRate, double commission) {
        super(number, date, user, amount);
        this.counterparty = counterparty;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.commission = commission;
    }

    /**
     * Возвращает тип документа ("Заявка на оплату").
     *
     * @return строковое представление типа документа
     */
    @Override
    public String getType() {
        return "Заявка на оплату";
    }

    /**
     * Возвращает контрагента (получателя платежа).
     *
     * @return наименование контрагента
     */
    public String getCounterparty() {
        return counterparty;
    }

    /**
     * Возвращает валюту платежа.
     *
     * @return код валюты (3 символа)
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Возвращает курс обмена валюты.
     *
     * @return текущий курс обмена
     */
    public double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Возвращает комиссию за перевод.
     *
     * @return размер комиссии
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Возвращает описание заявки на оплату в формате:
     * "Заявка на оплату №[номер] от [дата], Сумма: [сумма] [валюта]".
     *
     * @return строку с описанием платежного поручения
     */
    @Override
    public String getDescription() {
        return String.format("Заявка на оплату №%s от %s, Сумма: %.2f %s",
                number, date.toString(), amount, currency);
    }
}