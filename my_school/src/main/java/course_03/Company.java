package course_03;

/**
 * 【3.2】【题】公司薪水
 * 有一个abstract 类，类名为Employee。
 * Employee的子类有YearWorker、MonthWorker和WeekWorker。
 * YearWorker对象按年领取薪水，MonthWorker按月领取薪水，WeekWorker按周领取薪水。
 * Employee类有一个abstract 方法：
 * public abstract double earnings();  子类必须重写父类的earnings()方法，给出各自领取报酬的具体方式。
 * 有一个Company类，该类用Employee数组作为成员，
 * Employee数组的单元可以是YearWorker对象的上转型对象、MonthWorker对象的上转型对象或WeekWorker对象的上转型对象。
 * 程序能输出Company对象一年需要支付的薪水总额。
 *
 * @since 2022-4-6
 */
class Company {
    public static void main(String[] args) {
        Employee[] em = new Employee[3];
        em[0] = new YearWorker(10000.0);
        em[1] = new MonthWorker(1000.0);
        em[2] = new WeekWorker(100.0);
        double sum = em[0].earnings() + em[1].earnings() + em[2].earnings();
        System.out.println("总工资为:" + sum);
    }
}

abstract class Employee {
    /**
     * 每年需要支付的薪水
     *
     * @return double
     */
    abstract double earnings();
}

class MonthWorker extends Employee {
    final double monthSalary;

    MonthWorker(double s) {
        monthSalary = s;
    }

    @Override
    double earnings() {
        return 12 * monthSalary;
    }

}

class WeekWorker extends Employee {
    final double weekSalary;

    WeekWorker(double s) {
        weekSalary = s;
    }

    @Override
    double earnings() {
        return 52 * weekSalary;
    }
}

class YearWorker extends Employee {
    final double yearSalary;

    YearWorker(double s) {
        yearSalary = s;
    }

    @Override
    double earnings() {
        return yearSalary;
    }

}