package ru.nstu.avtf.lab1.gui;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import ru.nstu.avtf.lab1.gui.alerts.ExceptionAlert;
import ru.nstu.avtf.lab1.gui.alerts.InformationAlert;

import java.io.File;
import java.io.IOException;

import static ru.nstu.avtf.lab1.gui.ListContainer.*;
import static ru.nstu.avtf.lab1.gui.StageLoader.*;

public class MainWindow {
    private static final FileChooser CHOOSER = new FileChooser();

    public MenuItem newListMenuItem = new MenuItem();
    public MenuItem openListMenuItem = new MenuItem();
    public MenuItem saveListMenuItem = new MenuItem();
    public MenuItem closeMenuItem = new MenuItem();
    public MenuItem aboutMenuItem = new MenuItem();

    public Button addToEndButton = new Button();
    public Button addMenuItem = new Button();
    public Button removeMenuItem = new Button();
    public Button sortMenuItem = new Button();

    public TextArea textArea = new TextArea();

    public MainWindow() {
        refreshListView();
    }

    public void pressNewList() {
        resetList();
        refreshListView();
    }

    public void pressOpenList() {
        openList();
        refreshListView();
    }

    public void pressSaveList() {
    }

    public void pressClose() {
        ((Stage) textArea.getScene().getWindow()).close();
    }

    public void pressAbout() {
        new InformationAlert(
                "О программе",
                "Лабораторная №2. Связный список с графическим интерфейсом",
                "Н.В. Путько, АММ2-20"
                ).showAndWait();
    }

    public void pressAddToEnd() {
        showDialog(loadAddToEndDialog());
        refreshListView();
    }

    public void pressAdd() {
        showDialog(loadAddDialog());
        refreshListView();
    }

    public void pressRemove() {
        showDialog(loadRemoveDialog());
        refreshListView();
    }

    public void pressSort() {
        getList().sort();
        refreshListView();
    }

    /**
     * Показать {@link Stage} в виде диалогового окна
     */
    private static void showDialog(Stage stage) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.centerOnScreen();
        stage.showAndWait();
    }

    /**
     * Обновить отображение списка в окне
     */
    private void refreshListView() {
        StringBuilder result = new StringBuilder();
        int index = 0;
        for (int i : getList()) {
            result.append(String.format("[\t%d] %d\n", index, i));
            index++;
        }
        textArea.clear();
        textArea.setText(result.toString());
    }

    private void openList() {
        CHOOSER.setTitle("Открыть список...");
        File file = CHOOSER.showOpenDialog(textArea.getScene().getWindow());
        if (file == null) {
            return;
        }
        try {
            readFromFile(file);
        } catch (IOException | NumberFormatException e) {
            new ExceptionAlert(
                    "Ошибка",
                    "Не удалось прочитать файл",
                    "Неправильный формат файла",
                    e).showAndWait();
        }
    }
}
