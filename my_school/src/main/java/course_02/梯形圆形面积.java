package course_02;

/**
 * 【2.1】【题】梯形圆形面积
 * 编写一个Java应用程序，该程序中有2个类：Ladder和Circle，分别用来刻画“梯形”和“圆形”。具体要求如下：
 * a) Ladder类具有类型为double的私有属性上底、下底、高，具有返回面积的功能。
 * b) Circle类具有类型为double的私有属性半径，具有返回周长、面积的功能。
 * c)在一个新的public类中创建并使用两个类的对象：
 * Ladder类：创建对象并初始化，然后计算面积并输出
 * Circle类：创建对象并初始化，然后计算周长、面积并输出。
 *
 * @since 2022-3-23
 */
@SuppressWarnings("NonAsciiCharacters")
public class 梯形圆形面积 {
    public static void main(String[] args) {
        Ladder la = new Ladder(2.00, 3.00, 2.00);
        Circle ci = new Circle(4.00);//半径
        System.out.println("梯形面积是" + la.getArea());
        System.out.println("圆形周长是" + ci.getRound());
        System.out.println("圆形面积是" + ci.getArea());
    }
}

record Ladder(double above, double bottom, double height) {
    double getArea() {
        return (above + bottom) * height / 2;
    }
}

record Circle(double radius) {
    double getRound() {
        return radius * 6.28;
    }

    double getArea() {
        return 3.14 * radius * radius;
    }
}
