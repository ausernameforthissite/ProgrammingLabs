package ru.nstu.avtf.lab1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiLauncher extends Application {

    private static final String MAIN_WINDOW_PATH = "/mainWindow.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_WINDOW_PATH));

        Scene scene = loader.load();
        stage.setScene(scene);
        stage.show();
    }
}
