package ru.nstu.avtf.lab1.gui.alerts;

public class NumberFormatExceptionAlert extends ExceptionAlert {
    public NumberFormatExceptionAlert(Exception e) {
        super("Ошибка", "Неверный формат числа", "В текстовом поле должны быть только цифры", e);
    }
}
