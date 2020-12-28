package ru.nstu.avtf.lab1.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nstu.avtf.lab1.gui.alerts.NumberFormatExceptionAlert;
import ru.nstu.avtf.lab1.gui.alerts.UnknownExceptionAlert;

import static ru.nstu.avtf.lab1.gui.ListContainer.*;

public class AddDialog {
    public TextField indexTextField = new TextField();
    public TextField dataTextField = new TextField();
    public Button okButton = new Button();
    public Button cancelButton = new Button();

    public void pressOk() {
        try {
            System.out.println("AddDialog before:");
            System.out.println(getListView());
            System.out.println(getList());

            int data = Integer.parseInt(dataTextField.getText());
            int index = Integer.parseInt(indexTextField.getText());

            getList().add(data, index);
            refreshListView();

            System.out.println("AddDialog after:");
            System.out.println(getListView());
            System.out.println(getList());

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
        ((Stage) indexTextField.getScene().getWindow()).close();
    }
}
