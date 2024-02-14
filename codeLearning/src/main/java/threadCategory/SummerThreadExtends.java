package threadCategory;

public class SummerThreadExtends {

    private static void firstAttemptSameObjectNoLock() {
        /* 要是两个线程共用一个对象，他们的数据会共享，并只产生三行数据。
         * 这里因为不加锁，导致两个线程会交错进行*/
        PrintWords p = new PrintWords("NO.1");
        Thread t = new Thread(p);
        Thread t2 = new Thread(p);
        t.setName("January_1st");
        t2.setName("January_2nd");
        t.start();
        t2.start();
    }

    private static void secondAttemptHaveIdentical() {
        /* 要是两个线程共用一个对象，他们的数据会共享，并只产生三行数据。
         * 这里因为加了锁，第一个线程结束时，第二个线程才能开始，此时i等于3，导致第二个线程刚开始就结束了*/
        PrintWords p = new PrintWords("NO.2");
        Thread t = new Thread(p);
        Thread t2 = new Thread(p);
        t.setName("February_1st");
        t2.setName("February_2nd");
        t.start();
        t2.start();
    }

    private static void thirdAttemptNoIdentical() {
        /* 要是两个线程不共用对象对象，他们的数据不会共享，正常的产生六行数据
         * 运行结果依然是两个线程结果交错，可以知道只有同一个对象的锁才有用*/
        PrintWords p3 = new PrintWords("NO.3");
        PrintWords p4 = new PrintWords("NO.4");
        Thread t3 = new Thread(p3);
        Thread t4 = new Thread(p4);
        t3.setName("March_1st");
        t4.setName("March_2nd");
        t3.start();
        t4.start();
    }

    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
            System.out.println("--------Begin---------\nThe first attempt for object identical but no synchronized(three row)");
            firstAttemptSameObjectNoLock();
            Thread.sleep(1000);
            System.out.println("The second attempt for object identical(three row)");
            secondAttemptHaveIdentical();
            Thread.sleep(1000);
            System.out.println("The third attempt for no object identical(six row)");
            thirdAttemptNoIdentical();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class PrintWords extends Thread {
    int i = 0;
    String[] str = {"cat", "dog"};
    String name;

    public PrintWords(String n) {
        name = n;
    }

    private void theMonthlyAnimal() {
        while (i < 3) {
            System.out.println(name + " " + Thread.currentThread().getName() + " " + str[++i % 2] + " The current i = " + i);
        }
    }

    private synchronized void runTheMonthAnimal() {
        String threadName = Thread.currentThread().getName();
        System.out.println("---------" + threadName + ".begin---------");
        theMonthlyAnimal();
    }

    @Override
    public void run() {
        if (name.equals("NO.1")) {
            theMonthlyAnimal();
        } else {
            runTheMonthAnimal();
        }
    }
}