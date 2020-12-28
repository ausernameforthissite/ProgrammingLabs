package ru.nstu.avtf.lab1.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nstu.avtf.lab1.gui.alerts.NumberFormatExceptionAlert;
import ru.nstu.avtf.lab1.gui.alerts.UnknownExceptionAlert;
import ru.nstu.avtf.lab1.gui.alerts.WarningAlert;

import static ru.nstu.avtf.lab1.gui.ListContainer.getList;

/**
 * Диалог удаления элемента под заданным индексом
 */
public class RemoveDialog {
    public TextField indexTextField = new TextField();
    public Button okButton = new Button();
    public Button cancelButton = new Button();

    /**
     * Обработать нажатие кнопки "OK"
     */
    public void pressOk() {
        try {
            int index = Integer.parseInt(indexTextField.getText());
            boolean success = getList().remove(index);
            if (success) {
                close();
            } else {
                new WarningAlert(
                        "Ошибка",
                        "Не удалось удалить элемент по заданному индексу",
                        "Элемент с данным индексом не существует, либо список пуст").showAndWait();
            }
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
