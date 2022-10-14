package threadCategory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerAttempt {
    public static void main(String[] args) {
        methodTimer();
        methodScheduledPool();
    }

    private static void methodTimer() {
        System.out.println("一个Timer可以给多个任务定时，但如果其中一个任务异常(如果不用try-catch语句包围)，会影响其他任务。");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(Thread.currentThread().getName() + " 初始时间: " + sdf.format(new Date()));
        Timer timer = new Timer("dio");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 当前1时间: " + sdf.format(new Date()));
            }
        }, 1000, 2000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 当前2时间: " + sdf.format(new Date()));
            }
        }, 0, 3000);
    }

    private static void methodScheduledPool() {
        System.out.println("Schedule方法与timer不同的是，他可以去运行Runnable方法，以及不会被因为一个任务异常而影响其他任务");
        System.out.println();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(3);
        scheduledPool.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 当前3时间: " + sdf.format(new Date()));
            }
        }, 1, 2, TimeUnit.SECONDS);

        scheduledPool.scheduleAtFixedRate(new TimerAttemptMyRunnable(), 0, 3, TimeUnit.SECONDS);
    }
}

class TimerAttemptMyRunnable implements Runnable {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    public synchronized void run() {
        System.out.println(Thread.currentThread().getName() + " 当前4时间: " + sdf.format(new Date()));
    }
}