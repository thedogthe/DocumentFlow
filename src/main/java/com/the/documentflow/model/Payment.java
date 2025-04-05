package com.the.documentflow.model;

import java.time.LocalDate;

/**
 * Класс, представляющий платёжный документ в системе документооборота.
 * Наследует базовые свойства от {@link Document} и добавляет информацию о сотруднике.
 */
public class Payment extends Document {
    /** Сотрудник, связанный с платежом */
    private String employee;

    /**
     * Создает новый платёжный документ.
     *
     * @param number номер документа (уникальный идентификатор)
     * @param date дата создания документа
     * @param user пользователь, создавший документ
     * @param amount сумма платежа
     * @param employee сотрудник, ответственный за платёж
     */
    public Payment(String number, LocalDate date, String user, double amount, String employee) {
        super(number, date, user, amount);
        this.employee = employee;
    }

    /**
     * Возвращает тип документа ("Платёжка").
     *
     * @return строковое представление типа документа
     */
    @Override
    public String getType() {
        return "Платёжка";
    }

    /**
     * Возвращает сотрудника, связанного с платежом.
     *
     * @return имя сотрудника
     */
    public String getEmployee() {
        return employee;
    }

    /**
     * Возвращает описание платежа в формате:
     * "Платёжка №[номер] от [дата], Сумма: [сумма]".
     *
     * @return строку с описанием платежа
     */
    @Override
    public String getDescription() {
        return String.format("Платёжка №%s от %s, Сумма: %.2f",
                number, date.toString(), amount);
    }
}