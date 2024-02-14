package threadCategory;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CreateThread {
    public static void main(String[] args) {
        runJuly29thCallable(3);
        runJuly29thCallable(6);
        new July29thThread("Sum9", 9).start();
        new July29thThread("Sum12", 12).start();
        new Thread(new July29thRunnable("Sum15", 15)).start();
        new Thread(new July29thRunnable("Sum18", 18)).start();
    }

    private static void runJuly29thCallable(int startSum01) {
        Callable<String> callable = new July29thCallable(startSum01);
        FutureTask<String> f1 = new FutureTask<>(callable);
        Thread t1 = new Thread(f1);
        t1.start();
        try {
            String result1 = f1.get();
            System.out.println("The sum " + startSum01 + result1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class July29thCallable implements Callable<String> {
    private final int n;

    July29thCallable(int n) {
        this.n = n;
    }

    @Override
    public String call() {
        int sum = 0;
        for (int i = 1; i < n + 1; i++) {
            sum += i;
        }
        return " The result of July29thCallable is " + sum;
    }
}

class July29thThread extends Thread {
    private final int n;

    July29thThread(String name, int n) {
        super(name);
        this.n = n;
    }


    @Override
    public void run() {
        int sum02 = 0;
        for (int i = 1; i < n + 1; i++) {
            sum02 += i;
        }
        System.out.println(Thread.currentThread().getName() + " The result of July29thThread is " + sum02);
    }
}

class July29thRunnable implements Runnable {
    private final int n;
    private final String name;

    public July29thRunnable(String name, int n) {
        this.name = name;
        this.n = n;
    }

    @Override
    public void run() {
        int sum03 = 0;
        for (int i = 1; i < n + 1; i++) {
            sum03 += i;
        }
        System.out.println(name + " The result of July29thRunnable is" + sum03);
    }
}