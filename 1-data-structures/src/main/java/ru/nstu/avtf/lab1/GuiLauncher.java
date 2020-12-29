package ru.nstu.avtf.lab1;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nstu.avtf.lab1.gui.StageLoader;

public class GuiLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        StageLoader.loadMainWindow().show();
    }
}
