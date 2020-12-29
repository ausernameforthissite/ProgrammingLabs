package ru.nstu.avtf.lab1.gui.alerts;

import javafx.scene.control.Alert;

public class InformationAlert extends Alert {
    public InformationAlert(String title, String header, String content) {
        super(AlertType.INFORMATION);
        setTitle(title);
        setHeaderText(header);
        setContentText(content);
    }
}
