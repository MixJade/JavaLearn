package threadCategory;

import java.util.concurrent.*;

public class ThreadPool {

    public static void main(String[] args) {
        poolRunnable();
        poolCallable();
    }

    private static void poolRunnable() {
        /* 七个参数：核心线程数量，最大线程数，临时线程存活时间，存活时间单位，指定任务队列，指定线程工厂，拒绝策略(其实后面两个可以不加，有默认值）*/
        ExecutorService threadPool = new ThreadPoolExecutor(3, 5, 6, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        Runnable myRunnable = new August1stMyRunnable();
        threadPool.execute(myRunnable);
        threadPool.execute(myRunnable);
        threadPool.execute(myRunnable);
        threadPool.shutdown();
    }

    private static void poolCallable() {
        ExecutorService threadPool2 = new ThreadPoolExecutor(3, 5, 6, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        Callable<String> myCallable = new August1stMyCallable();
        Future<String> future01 = threadPool2.submit(myCallable);
        Future<String> future02 = threadPool2.submit(myCallable);
        Future<String> future03 = threadPool2.submit(myCallable);
        threadPool2.shutdown();
        try {
            System.out.println(future01.get());
            System.out.println(future02.get());
            System.out.println(future03.get());
        } catch (Exception e) {
            System.out.println("出错了!!!!!");
        }

    }
}

class August1stMyRunnable implements Runnable {
    private int i = 1;

    @Override
    public synchronized void run() {
        System.out.println(Thread.currentThread().getName() + " threadPool attempt " + i);
        i++;
    }
}

class August1stMyCallable implements Callable<String> {
    private int i = 0;

    @Override
    public synchronized String call() {
        i++;
        return Thread.currentThread().getName() + " " + i;
    }
}