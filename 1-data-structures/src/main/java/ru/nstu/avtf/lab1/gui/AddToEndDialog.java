package ru.nstu.avtf.lab1.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nstu.avtf.lab1.gui.alerts.NumberFormatExceptionAlert;
import ru.nstu.avtf.lab1.gui.alerts.UnknownExceptionAlert;

import static ru.nstu.avtf.lab1.gui.ListContainer.getList;

public class AddToEndDialog {
    public TextField dataTextField = new TextField();
    public Button okButton = new Button();
    public Button cancelButton = new Button();

    public void pressOk() {
        try {
            int data = Integer.parseInt(dataTextField.getText());
            getList().add(data);
            close();
        } catch (NumberFormatException nfe) {
            new NumberFormatExceptionAlert(nfe).showAndWait();
        } catch (Exception e) {
            new UnknownExceptionAlert(e).showAndWait();
        }
    }

    public void pressCancel() {
        close();
    }

    private void close() {
        ((Stage) dataTextField.getScene().getWindow()).close();
    }
}
