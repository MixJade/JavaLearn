package threadCategory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool {
    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
            methodFixThread();
            Thread.sleep(2000);
            methodFollowThread();
            Thread.sleep(2000);
            methodOnlyOneThread();
        }catch (InterruptedException e) {
            System.out.println("被打断");
        }
    }

    private static void methodFixThread() {
        System.out.println("-----First:the fix----");
        int k1 = 0;
        //1.创建固定数量的线程池
        ExecutorService fixThread = Executors.newFixedThreadPool(3);
        August9thMyRunnable myRunnable = new August9thMyRunnable();
        while (k1++ < 5) fixThread.execute(myRunnable);
        fixThread.shutdown();
    }

    private static void methodFollowThread() {
        System.out.println("-----Second:the follow----");
        int k2 = 0;
        //3.创建一个只有一个线程的线程池
        ExecutorService followThread = Executors.newCachedThreadPool();
        August9thMyRunnable myRunnable1 = new August9thMyRunnable();
        while (k2++ < 5) followThread.execute(myRunnable1);
        followThread.shutdown();
    }
    private static void methodOnlyOneThread() {
        System.out.println("-----Third:the only one----");
        int k3 = 0;
        //2.创建线程数量随着任务数量增加的线程池
        ExecutorService followThread = Executors.newSingleThreadExecutor();
        August9thMyRunnable myRunnable1 = new August9thMyRunnable();
        while (k3++ < 5) followThread.execute(myRunnable1);
        followThread.shutdown();
    }
}

class August9thMyRunnable implements Runnable {
    private int i = 0;

    @Override
    public synchronized void run() {
        System.out.println("第 " + ++i + " 次运行, " + Thread.currentThread().getName());
    }
}

