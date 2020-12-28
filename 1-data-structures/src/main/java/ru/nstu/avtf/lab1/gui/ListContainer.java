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

    /**
     * Обратиться к единственному связному списку в приложении
     *
     * @return связный список
     */
    public static MyLinkedList<Integer> getList() {
        return list;
    }

    /**
     * Очистить связный список
     */
    public static void resetList() {
        list = new MyLinkedList<>();
    }

    /**
     * Построчно прочитать числа из файла и добавить в список
     *
     * @param file файл
     * @throws IOException ошибки чтения
     */
    public static void readFromFile(File file) throws IOException {
        try (Stream<String> stream = Files.lines(file.toPath())) {
            resetList();
            stream.forEach(s -> list.add(Integer.parseInt(s)));
        }
    }

    /**
     * Построчно записать числа из списка в файл
     *
     * @param file файл
     * @throws IOException ошибки записи
     */
    public static void saveToFile(File file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            for (Integer integer : list) {
                writer.write(integer.toString());
                writer.newLine();
            }
        }
    }

}
