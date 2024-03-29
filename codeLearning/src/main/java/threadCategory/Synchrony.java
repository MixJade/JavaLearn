package threadCategory;

public class Synchrony {
    public static void main(String[] args) {
        July30thAccountant accountant01 = new July30thAccountant("MixJade", 3000);
        (new July30thReapThread("天一", accountant01, 1000.0)).start();
        (new July30thReapThread("天二", accountant01, 1000.0)).start();
        (new July30thReapThread("大爷", accountant01, true, 1000.0)).start();
        (new July30thReapThread("二爷", accountant01, true, 2000.0)).start();
        (new July30thReapThread("三爷", accountant01, true, 1500.0)).start();
        try {
            Thread.sleep(10000);
            System.exit(0);
        } catch (InterruptedException e) {
            System.out.print("I am breaker");
        }

    }
}


class July30thAccountant {
    private final String idCard;
    private double money;

    public July30thAccountant(String idCard, double money) {
        this.idCard = idCard;
        this.money = money;
    }

    public synchronized void fetch(double m) {
        try {
            if (money >= m) {
                money -= m;
                System.out.println("账户:" + idCard + " 取出:" + m + " 余额：" + money + " 取款人:" + Thread.currentThread().getName());
            }
            this.notifyAll();
            this.wait();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        }
    }

    public synchronized void deposit(double m) {
        try {
            if (money <= 1000) {
                money += m;
                System.out.println("账户:" + idCard + " 存入:" + m + " 余额：" + money + " 存款人:" + Thread.currentThread().getName());
            }
            this.wait();
            this.notifyAll();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        }
    }

}

class July30thReapThread extends Thread {
    private final July30thAccountant accountant;
    private final double operateMoney;
    private boolean saveMoney = false;

    July30thReapThread(String name, July30thAccountant accountant, boolean saveMoney, Double operateMoney) {
        super(name);
        this.accountant = accountant;
        this.saveMoney = saveMoney;
        this.operateMoney = operateMoney;
    }

    July30thReapThread(String name, July30thAccountant accountant, Double operateMoney) {
        super(name);
        this.accountant = accountant;
        this.operateMoney = operateMoney;
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            if (!saveMoney) {
                try {
                    accountant.fetch(operateMoney);
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (saveMoney) {
                try {
                    accountant.deposit(operateMoney);
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}