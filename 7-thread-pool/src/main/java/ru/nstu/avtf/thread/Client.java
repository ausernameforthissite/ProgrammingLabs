package ru.nstu.avtf.thread;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Класс, который непосредственно обраащется к пулу
 */
public class Client {

    private static CountDownLatch latch;

    /**
     * Протестировать программу с фиксированным пулом потоков
     *
     * @param rows      столбцы матрицы
     * @param columns   строки матрицы
     * @param threads   число потоков в пуле
     * @param runnables число задач, которые будут добавлены в пул
     * @return время выполнения в миллисекундах
     * @throws InterruptedException прерывание потока
     */
    public static long testThreadPool(int rows, int columns, int threads, int runnables) throws InterruptedException {
        // распределим работу между задачами
        CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> matrix = Utils.getRandomMatrix(rows, columns);

        long startTime = System.currentTimeMillis();
        int partSize = (int) Math.ceil(1.0 * rows / runnables);
        if (partSize == 0) {
            partSize = 1;
        }

        ArrayList<SquaringTask> tasks = new ArrayList<>();
        int firstRow = 0;
        int actualRunnables = 0;
        while (firstRow < rows) {
            int lastRow = Integer.min(firstRow + partSize - 1, rows - 1);
            SquaringTask task = new SquaringTask(matrix, firstRow, lastRow, columns);
            firstRow += partSize;
            tasks.add(task);
            actualRunnables++;
        }

        // посчитаем законченные задания, чтобы завершить работу пула
        latch = new CountDownLatch(actualRunnables);
        ThreadPool pool = new ThreadPool(threads);
        for (SquaringTask task: tasks) {
            pool.execute(task);
        }
        // ждём завершения всех заданий, чтобы выключить пул
        latch.await();
        pool.shutdown();

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Протестировать программу с произвольным созданием потоков
     *
     * @param rows    столбцы матрицы
     * @param columns строки матрицы
     * @param threads потоки, между которыми будет распределена матрицы
     * @return время выполнения в миллисекундах
     * @throws InterruptedException прерывание потока
     */
    public static long testManyThreads(int rows, int columns, int threads) throws InterruptedException {
        // распределим работу между задачами
        CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> matrix = Utils.getRandomMatrix(rows, columns);

        long startTime = System.currentTimeMillis();
        int partSize = (int) Math.ceil(1.0 * rows / threads);
        if (partSize == 0) {
            partSize = 1;
        }

        ArrayList<SquaringTask> tasks = new ArrayList<>();
        int firstRow = 0;
        int actualThreads = 0;
        while (firstRow < rows) {
            int lastRow = Integer.min(firstRow + partSize - 1, rows - 1);
            SquaringTask task = new SquaringTask(matrix, firstRow, lastRow, columns);
            firstRow += partSize;
            tasks.add(task);
            actualThreads++;
        }

        // посчитаем законченные задания, чтобы завершить работу пула
        latch = new CountDownLatch(actualThreads);
        for (SquaringTask task : tasks) {
            new Thread(task).start();
        }
        // ждём завершения всех заданий, чтобы выключить пул
        latch.await();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Протестировать программу с одним потоком
     *
     * @param rows    столбцы матрицы
     * @param columns строки матрицы
     * @return время выполнения в миллисекундах
     * @throws InterruptedException прерывание потока
     */
    public static long testOneThread(int rows, int columns) throws InterruptedException {
        CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> matrix = Utils.getRandomMatrix(rows, columns);

        long startTime = System.currentTimeMillis();
        SquaringTask task = new SquaringTask(matrix, 0, rows - 1, columns);
        latch = new CountDownLatch(1);
        new Thread(task).start();
        latch.await();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Задача по возведению чисел из матрицы в квадрат
     */
    public static class SquaringTask implements Runnable {

        private final CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> matrix;
        private final int firstRow;
        private final int lastRow;
        private final int columns;

        public SquaringTask(CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> matrix, int firstRow, int lastRow,
                            int columns) {
            this.matrix = matrix;
            this.firstRow = firstRow;
            this.lastRow = lastRow;
            this.columns = columns;
        }

        @Override
        public void run() {
            // сохраняем часть матрицы после возведения в квадрат у себя
            CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> tmp = new CopyOnWriteArrayList<>();
            for (int i = firstRow; i <= lastRow; i++) {
                CopyOnWriteArrayList<Integer> row = new CopyOnWriteArrayList<>();
                for (int j = 0; j < columns; j++) {
                    int value = matrix.get(i).get(j);
                    row.add(value * value);
                }
                tmp.add(row);
            }

            // копируем часть матрицы в общую матрицу
            for (int i = firstRow; i <= lastRow; i++) {
                matrix.set(i, tmp.get(i - firstRow));
            }

            // сообщаем основному потоку, что работа завершена
            if (latch != null) {
                latch.countDown();
            }
        }
    }
}
