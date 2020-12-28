package ru.nstu.avtf.thread;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Пул потоков с очередью задач.
 */
public class ThreadPool {
    private final LinkedBlockingQueue<Runnable> queue;

    private boolean shutdown;

    /**
     * @param nThreads число потоков в пуле
     */
    public ThreadPool(int nThreads) {
        queue = new LinkedBlockingQueue<>();
        PoolWorker[] threads = new PoolWorker[nThreads];

        shutdown = false;

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    /**
     * Добавить задачу в очередь
     *
     * @param task задача
     */
    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    /**
     * Команда завершения всех потоков пула
     */
    public void shutdown() {
        shutdown = true;
    }

    /**
     * Поток, который постоянно обращается к очереди и исполняет задачи
     */
    private class PoolWorker extends Thread {
        @Override
        public void run() {
            // пока не было команды завершить работу, продолжаем опрос очереди
            while (!shutdown) {
                Runnable task;
                // ожидаем новую задачу
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("InterruptedException: " + e.getMessage());
                        }
                    }
                    task = queue.poll();
                }
                // выполняем задачу, взятую из очереди, не создавая новый поток
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("RuntimeException: " + e.getMessage());
                }
            }
        }
    }
}
