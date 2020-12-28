package ru.nstu.avtf.lab1.gui.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionAlert extends Alert {
    public ExceptionAlert(String title, String header, String content, Exception e) {
        super(AlertType.ERROR);
        setTitle(title);
        setHeaderText(header);
        setContentText(content);

        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String stackTrace = stringWriter.toString();

        Label stacktraceLabel = new Label("Стектрейс:");

        TextArea textArea = new TextArea(stackTrace);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(stacktraceLabel, 0, 0);
        gridPane.add(textArea, 0, 1);

        getDialogPane().setExpandableContent(gridPane);
    }
}
