package com.the.documentflow.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.time.LocalDate;

/**
 * Абстрактный класс, представляющий документ в системе документооборота.
 * Содержит общие поля и методы для всех типов документов.
 */
public abstract class Document {
    /** Номер документа (уникальный идентификатор). */
    protected String number;

    /** Дата создания документа. */
    protected LocalDate date;

    /** Пользователь, создавший документ. */
    protected String user;

    /** Сумма, связанная с документом. */
    protected double amount;

    /** Флаг выбора документа (используется в UI). */
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    /**
     * Создает новый документ с указанными параметрами.
     *
     * @param number номер документа
     * @param date дата создания
     * @param user пользователь-создатель
     * @param amount сумма
     */
    public Document(String number, LocalDate date, String user, double amount) {
        this.number = number;
        this.date = date;
        this.user = user;
        this.amount = amount;
    }

    /**
     * Возвращает номер документа.
     *
     * @return номер документа
     */
    public String getNumber() { return number; }

    /**
     * Устанавливает номер документа.
     *
     * @param number новый номер документа
     */
    public void setNumber(String number) { this.number = number; }

    /**
     * Возвращает дату создания документа.
     *
     * @return дата создания
     */
    public LocalDate getDate() { return date; }

    /**
     * Устанавливает дату создания документа.
     *
     * @param date новая дата
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Возвращает пользователя, создавшего документ.
     *
     * @return имя пользователя
     */
    public String getUser() { return user; }

    /**
     * Устанавливает пользователя, создавшего документ.
     *
     * @param user новое имя пользователя
     */
    public void setUser(String user) { this.user = user; }

    /**
     * Возвращает сумму, связанную с документом.
     *
     * @return сумма
     */
    public double getAmount() { return amount; }

    /**
     * Устанавливает сумму, связанную с документом.
     *
     * @param amount новая сумма
     */
    public void setAmount(double amount) { this.amount = amount; }

    /**
     * Возвращает тип документа (реализуется в подклассах).
     *
     * @return тип документа
     */
    public abstract String getType();

    /**
     * Возвращает описание документа (реализуется в подклассах).
     *
     * @return описание документа
     */
    public abstract String getDescription();

    /**
     * Проверяет, выбран ли документ (например, в таблице UI).
     *
     * @return {@code true}, если документ выбран
     */
    public boolean isSelected() {
        return selected.get();
    }

    /**
     * Возвращает свойство выбора документа (для JavaFX-привязок).
     *
     * @return свойство {@link BooleanProperty}
     */
    public BooleanProperty selectedProperty() {
        return selected;
    }

    /**
     * Устанавливает флаг выбора документа.
     *
     * @param selected {@code true}, если документ выбран
     */
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}