package ru.nstu.avtf.lab1.gui;

import ru.nstu.avtf.lab1.list.MyLinkedList;

/**
 * Утилитный класс для обращения к связному списку из всех контроллеров окон
 */
public class ListContainer {
    public static final MyLinkedList<Integer> LIST = new MyLinkedList<>();

    private ListContainer() {
    }
}
