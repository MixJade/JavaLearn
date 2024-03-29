package term_end;

/**
 * 4.编写一个Java应用程序，设计一个汽车类Vehicle，
 * 包含的成员属性有：车轮个数wheels和车重weight。
 * 车类Car是Vehicle的子类，其中包含属性载人数passenger_load。
 * 卡车Truck是Vehicle的子类，其中包含载人数passenger_load和载重量payload。
 * 每个类都有toString()，用于格式输出，并进行测试。
 *
 * @since 2022-6-13
 */
@SuppressWarnings("NonAsciiCharacters")
public class 汽车多态 {
    public static void main(String[] args) {
        Vehicle v = new Vehicle(4, 2);
        System.out.println(v);
        Car c = new Car(8, 2, 20);
        System.out.println(c);
        Truck t = new Truck(8, 3, 4, 10);
        System.out.println(t);
    }
}

class Vehicle {
    public final int wheels;
    public final double weight;

    public Vehicle(int wheels, double weight) {
        this.wheels = wheels;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return ("汽车A有" + wheels + "个轮子,它的重量是" + weight + "吨");
    }
}

class Car extends Vehicle {
    public final int passenger_load;

    public Car(int wheels, double weight, int passenger_load) {
        super(wheels, weight);
        this.passenger_load = passenger_load;
    }

    @Override
    public String toString() {
        return ("汽车B有" + wheels + "个轮子,它的重量是" + weight + "吨,能载" + passenger_load + "个人");
    }
}

class Truck extends Vehicle {
    public final double payload;
    public final int passenger_load;

    public Truck(int wheels, double weight, int passenger_load, double payload) {
        super(wheels, weight);
        this.passenger_load = passenger_load;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return ("汽车C有" + wheels + "个轮子,它的重量是" + weight + "吨,能载" + passenger_load + "个人,能装" + payload + "吨货");
    }
}