package ru.nstu.avtf.lab1.gui.alerts;

import javafx.scene.control.Alert;

public class WarningAlert extends Alert {
    public WarningAlert(String title, String header, String content) {
        super(AlertType.WARNING);
        setTitle(title);
        setHeaderText(header);
        setContentText(content);
    }
}
