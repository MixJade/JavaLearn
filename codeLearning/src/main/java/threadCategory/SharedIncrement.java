package threadCategory;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 5个线程读取一个列表，每读取完成一个元素，一个全局的int就加1
 *
 * @since 2024-10-09 17:58:26
 */
public class SharedIncrement {

    private static final int THREAD_COUNT = 5; // 线程数量


    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        MyOKO myOKO = new MyOKO();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new MySleepThread(i, myOKO, latch).start();
        }
        latch.await();  // 等待所有的线程结束
        System.out.println("All of jobs has processed");
    }
}

/**
 * 公共变量类
 */
class MyOKO {
    private static final AtomicInteger SUC_COUNT = new AtomicInteger(0);
    CopyOnWriteArrayList<String> EL_LIST = new CopyOnWriteArrayList<>();
    private final int LIST_SIZE;

    public MyOKO() {
        for (int i = 0; i < 20; i++) {
            EL_LIST.add("El-" + (i + 1));
        }
        LIST_SIZE = EL_LIST.size();
    }

    String getNextItem() {
        if (EL_LIST.isEmpty()) {
            return null;
        }
        return EL_LIST.remove(0);
    }

    synchronized String getProgress() {
        return SUC_COUNT.incrementAndGet() + "/" + LIST_SIZE;
    }
}

/**
 * 用于读取元素并计数的线程
 */
class MySleepThread extends Thread {
    private final MyOKO myOKO;
    private final CountDownLatch latch;

    public MySleepThread(int name, MyOKO myOKO, CountDownLatch latch) {
        super("T" + name);
        this.myOKO = myOKO;
        this.latch = latch;
    }

    @Override
    public void run() {
        String item;
        while ((item = myOKO.getNextItem()) != null) {
            try {
                Thread.sleep(1000); // 处理元素
                System.out.println(Thread.currentThread().getName() + " " + item + " " + myOKO.getProgress());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        latch.countDown();
    }
}