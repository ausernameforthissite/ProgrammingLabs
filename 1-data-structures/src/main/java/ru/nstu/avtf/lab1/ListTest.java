package ru.nstu.avtf.lab1;

import ru.nstu.avtf.lab1.list.MyLinkedList;

import java.util.Random;

public class ListTest {
    private static final int ARRAY_SIZE = 11;
    private static final int MIN_NUMBER = 11;
    private static final int MAX_NUMBER = 99;

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();

        System.out.println("Пустой список:");
        System.out.println(list);

        for (int i = 0; i < ARRAY_SIZE; i++) {
            list.add(getRandomNumber());
        }
        System.out.println("Добавим случайные элементы:");
        System.out.println(list);

        list.remove(0);
        System.out.println("Удалим первый элемент:");
        System.out.println(list);

        list.remove(list.size() - 1);
        System.out.println("Удалим последний элемент:");
        System.out.println(list);

        list.remove(list.size() / 2);
        System.out.println("Удалим элемент посередине:");
        System.out.println(list);

        list.add(getRandomNumber(), 0);
        list.add(getRandomNumber());
        list.add(getRandomNumber(), list.size() / 2);
        System.out.println("Вставим случайные элементы в начало, конец и середину:");
        System.out.println(list);

        list.sort();
        System.out.println("Отсортируем:");
        System.out.println(list);

        System.out.println("Получим элемент с индексом 4: " + list.get(4));

        System.out.println("Вызовем у списка forEach() с выводом каждого элемента в консоль:");
        list.forEach(item -> System.out.print(item + " "));
    }

    private static Integer getRandomNumber() {
        Random random = new Random();
        return MIN_NUMBER + random.nextInt(MAX_NUMBER - MIN_NUMBER + 1);
    }
}
