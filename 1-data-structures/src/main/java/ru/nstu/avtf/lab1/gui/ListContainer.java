package ru.nstu.avtf.lab1.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import ru.nstu.avtf.lab1.list.MyLinkedList;

import java.util.stream.Collectors;

/**
 * Утилитный класс для обращения к связному списку из всех контроллеров окон
 */
public class ListContainer {
    private static MyLinkedList<Integer> list = new MyLinkedList<>();

    private static ListView<Integer> listView;

    private ListContainer() {
    }

    public static MyLinkedList<Integer> getList() {
        return list;
    }

    public static ListView<Integer> getListView() {
        return listView;
    }

    public static void setListView(ListView<Integer> listView) {
        ListContainer.listView = listView;
    }

    /**
     * Обновить отображение списка в окне
     */
    public static void refreshListView() {
        System.out.println("refreshListView()");
        System.out.println("\tbefore: " + listView.getItems());
        ObservableList<Integer> observableList
                = FXCollections.observableList(list.stream().collect(Collectors.toList()));
        listView.setItems(observableList);
        System.out.println("\tafter: " + listView.getItems());
    }
}
