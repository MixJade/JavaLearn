package course_02;

import java.util.Scanner;

/**
 * 【2.2】【题】矩形面积周长
 * 创建一个简单的表示矩形的Rectangle类，满足以下条件：
 * 1)定义两个成员变量height和width，表示矩形的长和宽，类型为整型
 * 2)定义一个getArea方法，返回矩形的面积
 * 3)定义一个getPerimeter方法，返回矩形的周长
 * 4)在main函数中，利用从键盘输入的2个数分别作为矩形的长和宽，调用getArea和getPerimeter方法，计算并返回矩形的面积和周长
 * 输入：
 * 输入2个正整数，中间用空格隔开，分别作为矩形的长和宽，例如：5 8
 * 输出：
 * 输出2个正整数，中间用空格隔开，分别表示矩形的面积和周长，例如：40 26
 *
 * @since 2022-3-23
 */
@SuppressWarnings("NonAsciiCharacters")
public class 矩形面积周长 {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle();
        System.out.println("输入2个正整数，例如：5 8");
        Scanner in = new Scanner(System.in);
        rect.width = in.nextInt();
        rect.height = in.nextInt();
        // "输出2个正整数，例如：40 26"
        System.out.println("矩形面积：" + rect.getArea() + " 周长：" + rect.getPerimeter());
        in.close();
    }
}

class Rectangle {
    int height;
    int width;

    public int getArea() {
        return width * height;//面积
    }

    public int getPerimeter() {
        return (width + height) * 2;//周长
    }
}