package ru.nstu.avtf.thread;

import static ru.nstu.avtf.thread.Client.*;

/**
 * Класс для автоматического тестирования вариантов программы с тред-пулом, произвольным числом потоков и единственным
 * потоком. Выводит в консоль таблицу со временем выполнения программы в миллисекундах
 */
public class Comparison {
    /**
     * Размер одной стороны квадратной матрицы
     */
    private static final int[] SIZES = {500, 1000, 1500, 2000, 2500, 3000};

    /**
     * Число потоков либо задач (при фиксированном пуле потоков)
     */
    private static final int[] RUNNABLES = {36, 24, 12, 6};

    /**
     * Число потоков в пуле
     */
    private static final int THREADS = 5;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Тредпул:");
        System.out.print("П\\Р");
        for (int size : SIZES) {
            System.out.print("\t" + size);
        }
        System.out.println();
        for (int runnables : RUNNABLES) {
            System.out.print(runnables);
            for (int size : SIZES) {
                System.out.print("\t" + testThreadPool(size, size, THREADS, runnables));
            }
            System.out.println();
        }

        System.out.println("Много потоков:");
        System.out.print("П\\Р");
        for (int size : SIZES) {
            System.out.print("\t" + size);
        }
        System.out.println();
        for (int runnables : RUNNABLES) {
            System.out.print(runnables);
            for (int size : SIZES) {
                System.out.print("\t" + testManyThreads(size, size, runnables));
            }
            System.out.println();
        }

        System.out.println("Один поток:");
        System.out.print("Р:");
        for (int size : SIZES) {
            System.out.print("\t" + size);
        }
        System.out.println();
        for (int size : SIZES) {
            System.out.print("\t" + testOneThread(size, size));
        }
        System.out.println();
    }
}
