package ru.nstu.avtf.thread;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final LinkedBlockingQueue<Runnable> queue;

    private boolean shutdown;

    public ThreadPool(int nThreads) {
        queue = new LinkedBlockingQueue<>();
        PoolWorker[] threads = new PoolWorker[nThreads];

        shutdown = false;

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    public void shutdown() {
        shutdown = true;
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable task;

            while (!shutdown) {
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

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("RuntimeException: " + e.getMessage());
                }
            }
        }
    }
}
