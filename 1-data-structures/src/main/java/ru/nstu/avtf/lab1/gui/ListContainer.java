package ru.nstu.avtf.lab1.gui;

import ru.nstu.avtf.lab1.list.MyLinkedList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

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

    public static void resetList() {
        list = new MyLinkedList<>();
    }

    public static void readFromFile(File file) throws IOException {
        try (Stream<String> stream = Files.lines(file.toPath())) {
            resetList();
            stream.forEach(s -> list.add(Integer.parseInt(s)));
        }
    }

    public static void saveToFile(File file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            for (Integer integer : list) {
                writer.write(integer.toString());
                writer.newLine();
            }
        }
    }

}
