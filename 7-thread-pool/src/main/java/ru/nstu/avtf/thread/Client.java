package ru.nstu.avtf.thread;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class Client {

    private static CountDownLatch latch;

    public static void main(String[] args) throws InterruptedException {
        long time = testThreadPool(100, 100, 5, 15);
        System.out.println("time:" + time + "ms");
    }

    public static long testThreadPool(int rows, int columns, int threads, int runnables) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        // распределим работу между задачами
        CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> matrix = Utils.getRandomMatrix(rows, columns);
        int partSize = (int) Math.ceil(1.0 * rows / runnables);
        if (partSize == 0) {
            partSize = 1;
        }
        System.out.println("Матрица:");
        System.out.println(matrix);
        System.out.println("Размер порции: " + partSize);

        ArrayList<SquaringTask> tasks = new ArrayList<>();
        int firstRow = 0;
        int actualRunnables = 0;
        while (firstRow < rows) {
            int lastRow = Integer.min(firstRow + partSize - 1, rows - 1);
            System.out.println("\tот " + firstRow + " до " + lastRow);
            SquaringTask task = new SquaringTask(matrix, firstRow, lastRow, columns);
            firstRow += partSize;
            tasks.add(task);
            actualRunnables++;
        }
        System.out.println("Всего раннаблов: " + actualRunnables);

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

        System.out.println("Матрица:");
        System.out.println(matrix);

        return endTime - startTime;
    }

    private static class SquaringTask implements Runnable {

        private CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> matrix;
        private int firstRow;
        private int lastRow;
        private int columns;

        public SquaringTask(CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> matrix, int firstRow, int lastRow,
                            int columns) {
            this.matrix = matrix;
            this.firstRow = firstRow;
            this.lastRow = lastRow;
            this.columns = columns;
        }

        @Override
        public void run() {
            CopyOnWriteArrayList<CopyOnWriteArrayList<Integer>> tmp = new CopyOnWriteArrayList<>();
            for (int i = firstRow; i <= lastRow; i++) {
                CopyOnWriteArrayList<Integer> row = new CopyOnWriteArrayList<>();
                for (int j = 0; j < columns; j++) {
                    int value = matrix.get(i).get(j);
                    row.add(value * value);
                }
                tmp.add(row);
            }
            for (int i = firstRow; i <= lastRow; i++) {
                matrix.set(i, tmp.get(i - firstRow));
            }
            if (latch != null) {
                latch.countDown();
                System.out.println("\tlatch: " + latch.getCount());
            }
        }
    }
}
