package ru.nstu.avtf.thread;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Utils {

    /**
     * Нижняя граница диапазона псевдослучайных чисел
     */
    private static final int MIN_NUMBER = 11;
    /**
     * Верхняя граница диапазона псевдослучайных чисел
     */
    private static final int MAX_NUMBER = 99;

    /**
     * Сгенерировать матрицу псевдослучайных двухзначных чисел
     *
     * @param rows строки
     * @param columns столбцы
     * @return матрица
     */
    public static CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> getRandomMatrix(int rows, int columns) {
        CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> result = new CopyOnWriteArrayList<>();
        for (int i = 0; i < rows; i++) {
            result.add(getRandomRow(columns));
        }
        return result;
    }

    /**
     * Сгенерировать строку матрицы псевдослучайных двухзначных чисел
     *
     * @param columns столбцы
     * @return строка матрицы
     */
    private static CopyOnWriteArrayList<Integer> getRandomRow(int columns) {
        CopyOnWriteArrayList<Integer> result = new CopyOnWriteArrayList<>();
        for (int i = 0; i < columns; i++) {
            result.add(getRandomNumber());
        }
        return result;
    }

    /**
     * @return псевдослучайное двухзначное число
     */
    private static Integer getRandomNumber() {
        Random random = new Random();
        return MIN_NUMBER + random.nextInt(MAX_NUMBER - MIN_NUMBER + 1);
    }
}
