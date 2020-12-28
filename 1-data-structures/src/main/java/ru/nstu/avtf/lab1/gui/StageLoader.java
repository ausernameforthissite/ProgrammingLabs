package ru.nstu.avtf.lab1.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Утилитный класс для загрузки FXML-файлов
 */
public class StageLoader {

    private static final String ADD_DIALOG = "/addDialog.fxml";
    private static final String ADD_TO_END_DIALOG = "/addToEndDialog.fxml";
    private static final String MAIN_WINDOW = "/mainWindow.fxml";
    private static final String REMOVE_DIALOG = "/removeDialog.fxml";

    private StageLoader() {
    }

    /**
     * Прочитать FXML из каталога с ресурсами и создать окно JavaFX
     *
     * @param path путь к FXML-файлу
     * @return окно
     */
    private static Stage load(String path) {
        FXMLLoader loader = new javafx.fxml.FXMLLoader(StageLoader.class.getResource(path));

        Scene scene = null;
        try {
            scene = loader.load();
        } catch (IOException ignored) {
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }

    public static Stage loadAddDialog() {
        return load(ADD_DIALOG);
    }

    public static Stage loadAddToEndDialog() {
        return load(ADD_TO_END_DIALOG);
    }

    public static Stage loadMainWindow() {
        return load(MAIN_WINDOW);
    }

    public static Stage loadRemoveDialog() {
        return load(REMOVE_DIALOG);
    }
}
