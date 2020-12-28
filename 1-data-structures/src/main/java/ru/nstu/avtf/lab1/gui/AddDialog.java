package ru.nstu.avtf.lab1.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nstu.avtf.lab1.gui.alerts.NumberFormatExceptionAlert;
import ru.nstu.avtf.lab1.gui.alerts.UnknownExceptionAlert;

import static ru.nstu.avtf.lab1.gui.ListContainer.*;

/**
 * Диалог добавления элемента под заданным индексом
 */
public class AddDialog {
    public TextField indexTextField = new TextField();
    public TextField dataTextField = new TextField();
    public Button okButton = new Button();
    public Button cancelButton = new Button();

    /**
     * Обработать нажатие кнопки "OK"
     */
    public void pressOk() {
        try {
            int data = Integer.parseInt(dataTextField.getText());
            int index = Integer.parseInt(indexTextField.getText());

            getList().add(data, index);

            close();
        } catch (NumberFormatException nfe) {
            new NumberFormatExceptionAlert(nfe).showAndWait();
        } catch (Exception e) {
            new UnknownExceptionAlert(e).showAndWait();
        }
    }

    /**
     * Обработать нажатие кнопки "Отмена"
     */
    public void pressCancel() {
        close();
    }

    /**
     * Закрыть диалог
     */
    private void close() {
        ((Stage) indexTextField.getScene().getWindow()).close();
    }
}
