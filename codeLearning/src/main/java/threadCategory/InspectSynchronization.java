package threadCategory;

public class InspectSynchronization {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.setMoney(200);
        bank.saveMoney(300);
        bank.takeMoney(150);
        Thread accountant, cashier;
        accountant = new Thread(bank);
        cashier = new Thread(bank);
        accountant.setName("Accountant");
        cashier.setName("Cashier");
        accountant.start();
        cashier.start();
    }
}

class Bank implements Runnable {
    int money = 200;
    int deposit = 0;
    int salary = 0;

    public void setMoney(int n) {
        money = n;
    }

    public void saveMoney(int d) {
        deposit = d;
    }

    public void takeMoney(int t) {
        salary = t;
    }

    public void run() {
        if (Thread.currentThread().getName().equals("Accountant")) saveOrTake(deposit);
        else if (Thread.currentThread().getName().equals("Cashier")) saveOrTake(salary);
    }

    public synchronized void saveOrTake(int amount) { //存取方法
        if (Thread.currentThread().getName().equals("Accountant")) {
            for (int i = 1; i <= 3; i++) {
                money = money + amount / 3;      //每存入amount/3，稍歇一下
                System.out.println("会计存入" + amount / 3 + "万,帐上有" + money + "万,休息一会再存");
                try {
                    Thread.sleep(1000);  //这时出纳仍不能使用saveOrTake方法
                } catch (InterruptedException e) {
                    System.out.println("Wake up");
                }
            }
        } else if (Thread.currentThread().getName().equals("Cashier")) {
            for (int i = 1; i <= 3; i++) {
                int amountMoney;//出纳取出的钱
                if (money >= 500) {
                    amountMoney = amount / 2;   //取出amount/2
                } else if (money >= 400) amountMoney = amount / 3;   //取出amount/3
                else if (money >= 200) amountMoney = amount / 5;  //取出amount/5
                else amountMoney = amount / 10;  //取出amount/10
                money = money - Math.min(amountMoney, money);
                System.out.println("出纳取出" + amountMoney + "万,帐上有" + money + "万,休息一会再取");
                try {
                    Thread.sleep(1000);    //这时会计仍不能使用saveOrTake方法
                } catch (InterruptedException e) {
                    System.out.println("Wake up");
                }
            }
        }
    }
}
