package course_02;

import java.util.Scanner;

/**
 * 【2.3】【题】两点间距
 * 编写一个表示二维平面上的点的类MyPoint，满足以下条件：
 * 1)定义private的成员变量x和y，表示点的x和y坐标，类型为double
 * 2)定义两个MyPoint的构造方法，一个构造方法不带参数，而且x和y的初始值为0，另一个构造方法有两个参数，参数名为x和y，类型为double，用这两个参数分别作为初始x和y坐标
 * 3)定义一个getD方法，有一个类型为MyPoint的对象参数，功能为返回当前对象和参数对象这两个坐标点的距离，返回值为double类型；
 * 求平方根的方法：Math.sqrt(x)
 * 4)编写测试的main方法，调用getD计算两个点之间的距离
 * 输入：
 * 输入2行数据， 总共4个有理数。每2个数据一组，表示一个点的x和y坐标，每行的2个数据用空格隔开。例如：
 * 200.1 200.2  P1
 * 200.3 200.4  P2
 * 输出：
 * 输出两个点之间的距离。例如：0.28284271247464315
 *
 * @since 2022-3-23
 */
@SuppressWarnings("NonAsciiCharacters")
public class 两点间距 {
    public static void main(String[] args) {
        System.out.println("输入两个点的坐标：(如200.1 200.2 200.3 200.4)");
        Scanner sc1 = new Scanner(System.in);
        // 200.1 200.2 200.3 200.4
        MyPoint p1 = new MyPoint(sc1.nextDouble(), sc1.nextDouble());
        MyPoint p2 = new MyPoint(sc1.nextDouble(), sc1.nextDouble());
        System.out.println("两点间距为：\n" + p1.getD(p2));//0.28284271247464315
    }
}

record MyPoint(double x, double y) {
    double getD(MyPoint p2) {
        return Math.sqrt(Math.pow(this.x - p2.x, 2) + Math.pow(this.y - p2.y, 2));
    }
}
