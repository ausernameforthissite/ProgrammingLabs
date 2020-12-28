package ru.nstu.avtf.lab1.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import ru.nstu.avtf.lab1.gui.alerts.InformationAlert;

import java.util.stream.Collectors;

import static ru.nstu.avtf.lab1.gui.ListContainer.LIST;

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

    public MainWindow() {
        refreshListView();
    }

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
        LIST.add(111);
        refreshListView();
    }

    public void pressRemove() {
    }

    public void pressSort() {
    }

    private void refreshListView() {

        ObservableList<Integer> observableList = FXCollections.observableList(
                LIST.stream().collect(Collectors.toList()));
        listView.setItems(observableList);
    }
}
