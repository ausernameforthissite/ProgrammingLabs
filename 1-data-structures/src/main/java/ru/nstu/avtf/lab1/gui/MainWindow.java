package ru.nstu.avtf.lab1.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ru.nstu.avtf.lab1.gui.alerts.InformationAlert;

public class MainWindow {
    public MenuItem newListMenuItem = new MenuItem();
    public MenuItem openListMenuItem = new MenuItem();
    public MenuItem saveListMenuItem = new MenuItem();
    public MenuItem closeMenuItem = new MenuItem();
    public MenuItem aboutMenuItem = new MenuItem();

    public Button addToEndButton = new Button();
    public Button addMenuItem = new Button();
    public Button removeMenuItem = new Button();
    public Button sortMenuItem = new Button();

    public ListView<Integer> listView = new ListView<>();

    public void pressNewList() {
    }

    public void pressOpenList() {
    }

    public void pressSaveList() {
    }

    public void pressClose() {
        ((Stage) listView.getScene().getWindow()).close();
    }

    public void pressAbout() {
        new InformationAlert(
                "О программе",
                "Лабораторная №2. Связный список с графическим интерфейсом",
                "Н.В. Путько, АММ2-20"
                ).showAndWait();
    }

    public void pressAddToEnd() {
    }

    public void pressAdd() {
    }

    public void pressRemove() {
    }

    public void pressSort() {
    }
}
