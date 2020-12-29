package ru.nstu.avtf.lab1.gui.alerts;

public class UnknownExceptionAlert extends ExceptionAlert {
    public UnknownExceptionAlert(Exception e) {
        super("Ошибка",
                "Неизвестная ошибка", "При выполнении операции над списком было кинуто исключение", e);
    }
}
