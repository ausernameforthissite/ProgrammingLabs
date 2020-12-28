package ru.nstu.avtf.lab1.gui;

import ru.nstu.avtf.lab1.list.MyLinkedList;

/**
 * Утилитный класс для обращения к связному списку из всех контроллеров окон
 */
public class ListContainer {
    private static MyLinkedList<Integer> list = new MyLinkedList<>();

    private ListContainer() {
    }

    public static MyLinkedList<Integer> getList() {
        return list;
    }

}
