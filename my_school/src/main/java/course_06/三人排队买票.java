package course_06;

/**
 * 【6.3】【题】三人排队买票
 * 参照例12-8，模拟三个人排队买票，
 * 张某、李某和赵某买电影票，售票员只有三张五元的钱，电影票5元钱一张。
 * 张某拿二十元一张的新人民币排在李的前面买票，
 * 李某排在赵的前面拿一张10元的人民币买票，
 * 赵某拿一张5元的人民币买票。
 *
 * @since 2022-5-11
 */
@SuppressWarnings("NonAsciiCharacters")
public class 三人排队买票 {
    public static void main(String[] args) {
        TheThingBegin d = new TheThingBegin();
        d.firstMan.start();
        d.secondMan.start();
        d.thirdMan.start();
    }
}

class TheThingBegin implements Runnable {
    final Thread firstMan;
    final Thread secondMan;
    final Thread thirdMan;
    final TicketHouse salesman;

    TheThingBegin() {
        firstMan = new Thread(this);
        firstMan.setName("张某");
        secondMan = new Thread(this);
        secondMan.setName("李某");
        thirdMan = new Thread(this);
        thirdMan.setName("赵某");
        salesman = new TicketHouse();
    }

    @Override
    public void run() {
        if (Thread.currentThread() == firstMan) {
            salesman.saleTicket(20);
        } else if (Thread.currentThread() == secondMan) {
            salesman.saleTicket(10);
        } else if (Thread.currentThread() == thirdMan) {
            salesman.saleTicket(5);
        }
    }
}

class TicketHouse {
    int fiveAmount = 3, tenAmount = 0, twentyAmount = 0;

    public synchronized void saleTicket(int money) {
        if (money == 20) {
            int x = fiveAmount * 5 + tenAmount * 10;
            if (x < 15) {
                waitOrBuy();
            }
            if (fiveAmount > 1 && tenAmount > 1) {
                fiveAmount = fiveAmount - 1;
                tenAmount = tenAmount - 1;
            } else if (fiveAmount >= 3) {
                fiveAmount = fiveAmount - 3;
            }
            twentyAmount = twentyAmount + 1;
            System.out.println("给" + Thread.currentThread().getName() + "入场卷,给20，找赎15元" + "，这时小二有" + fiveAmount + "张五元");
        } else if (money == 10) {
            while (fiveAmount < 1) {
                waitOrBuy();
            }
            fiveAmount = fiveAmount - 1;
            tenAmount = tenAmount + 1;
            System.out.println("给" + Thread.currentThread().getName() + "入场卷,给10，找赎5元" + "，这时小二有" + fiveAmount + "张五元");
        } else if (money == 5) {  //如果使用该方法的线程传递的参数是5,就不用等待
            fiveAmount = fiveAmount + 1;
            System.out.println("给" + Thread.currentThread().getName() + "入场卷,他的钱正好" + "，这时小二有" + fiveAmount + "张五元");
        }
        notifyAll();
    }

    private void waitOrBuy() {
        try {
            System.out.println("\n" + Thread.currentThread().getName() + "靠边等..." + "，这时小二有" + fiveAmount + "张五元");
            wait();//如果使用该方法的线程传递的参数是20须等待
            System.out.println("\n" + Thread.currentThread().getName() + "继续买票" + "，这时小二有" + fiveAmount + "张五元");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

