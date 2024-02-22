package course_03;

interface English {
    @SuppressWarnings("unused")
    void speakEnglish();
}

/**
 * 【3.4】【题】抽象类多态
 * 现在有乒乓球运动员和篮球运动员，乒乓球教练和篮球教练。
 * 为了出国交流，跟乒乓球相关的人员都需要学习英语。请用所有知识分析，这个案例中有哪些具体类，哪些抽象类，哪些接口，并用代码实现。
 * 思路：
 * 1)定义说英语接口	成员方法：说英语();
 * 2)定义抽象人类	成员变量：姓名，年龄；构造方法：无参，带参；成员方法：get/set方法，吃饭();
 * 3)定义抽象教练类，继承人类	构造方法：无参，带参；成员方法：教();
 * 4)定义抽象运动员类，继承人类	构造方法：无参，带参；成员方法：学习();
 * 5)定义具体篮球教练类，继承教练类	构造方法：无参，带参；成员方法：重写吃饭(){…}，重写教(){…}
 * 6)定义具体乒乓球教练类，继承教练类，实现说英语接口
 * 7)构造方法：无参，带参；成员方法：重写吃饭(){…}，重写教(){…}，重写说英语(){…}
 * 8)定义具体篮球运动员类，继承运动员类	构造方法：无参，带参；成员方法：重写吃饭(){…}，重写学习(){…}
 * 9)定义具体乒乓球运动员类，继承运动员类，实现说英语接口
 * 10)构造方法：无参，带参；成员方法：重写吃饭(){…}，重写学习(){…}，重写说英语(){…}
 * 11)定义测试类，写代码测试。
 *
 * @since 2022-4-6
 */
@SuppressWarnings("NonAsciiCharacters")
public class 抽象类多态 {
    public static void main(String[] args) {
        BasketballCoach b1 = new BasketballCoach();
        b1.name = "闫芳";
        b1.age = 70;
        PingPongCoach p1 = new PingPongCoach();
        p1.name = "马保国";
        p1.age = 69;
        BasketballMan b2 = new BasketballMan();
        b2.name = "蔡徐坤";
        b2.age = 25;
        PingPongMan p2 = new PingPongMan();
        p2.name = "孙笑川";
        p2.age = 32;
        System.out.println("\n出国交流：");
        System.out.println("\n  " + b1.name + ": \n篮球教练--" + b1.age + "岁。");
        b1.eat();
        b1.teach();
        System.out.println("\n  " + p1.name + ": \n乒乓球教练--" + p1.age + "岁。");
        p1.eat();
        p1.teach();
        p1.speakEnglish();
        System.out.println("\n  " + b2.name + ": \n篮球运动员--" + b2.age + "岁。");
        b2.eat();
        b2.learn();
        System.out.println("\n  " + p2.name + ": \n乒乓球运动员--" + p2.age + "岁。");
        p2.eat();
        p2.learn();
        p2.speakEnglish();
    }
}

abstract class Human {
    String name;
    int age;

    @SuppressWarnings("unused")
    abstract void eat();
}

abstract class Coach extends Human {
    @SuppressWarnings("unused")
    abstract void teach();
}

abstract class Sportsman extends Human {
    @SuppressWarnings("unused")
    abstract void learn();
}

class PingPongCoach extends Coach implements English {
    @Override
    void eat() {
        System.out.println("吃大海参");
    }

    @Override
    void teach() {
        System.out.println("教乒乓");
    }

    @Override
    public void speakEnglish() {
        System.out.println("学英语");
    }
}

class BasketballCoach extends Coach {
    @Override
    void eat() {
        System.out.println("吃大燕窝");
    }

    @Override
    void teach() {
        System.out.println("教篮球");
    }
}

class BasketballMan extends Sportsman {
    @Override
    void eat() {
        System.out.println("吃燕窝");
    }

    @Override
    void learn() {
        System.out.println("学篮球");
    }
}

class PingPongMan extends Sportsman implements English {
    @Override
    void eat() {
        System.out.println("吃海参");
    }

    @Override
    void learn() {
        System.out.println("学乒乓");
    }

    @Override
    public void speakEnglish() {
        System.out.println("学英语");
    }
}

