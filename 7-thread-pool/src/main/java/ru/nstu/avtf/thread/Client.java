package ru.nstu.avtf.thread;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class Client {

    private static CountDownLatch latch;

    public static void main(String[] args) throws InterruptedException {
        long time = testThreadPool(100, 100, 5, 15);
        System.out.println("time:" + time + "ms");
        time = testManyThreads(100, 100, 5);
        System.out.println("time:" + time + "ms");
        time = testOneThread(100, 100);
        System.out.println("time:" + time + "ms");
    }

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
        for (SquaringTask task: tasks) {
            new Thread(task).start();
        }
        // ждём завершения всех заданий, чтобы выключить пул
        latch.await();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

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
            }
        }
    }
}
