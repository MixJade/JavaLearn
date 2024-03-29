package course_02;

import java.util.Scanner;

/**
 * 【2.4】【题】复数计算
 * 定义一个复数(z=x+iy)类Complex，包含：
 * 1）两个属性：实部x和虚部y
 * 2）默认构造函数 Complex()，设置x=0,y=0
 * 3）构造函数：Complex(int i,int j)
 * 4）显示复数的方法：showComp()将其显示为如： 5+8i或5-8i 的形式。
 * 5）求两个复数的和的方法：public Complex addComp(Complex C2)
 * 6）求两个复数的差的方法：public Complex subComp(Complex C2)
 * 7）求两个复数的乘积的方法：public Complex multiComp(Complex C2)
 * 8）比较两个复数是否相等的方法：public boolean equalComp(Complex C2)
 * 9）在Application中测试该类的方法，实部x和虚部y可由键盘输入。
 *
 * @since 2022-3-23
 */
@SuppressWarnings("NonAsciiCharacters")
public class 复数计算 {
    public static void main(String[] args) {
        System.out.println("分别输入两个复数的实部，虚部。如(5 8 5 -8)");
        Scanner input = new Scanner(System.in);
        int x1 = input.nextInt();
        int y1 = input.nextInt();
        int x2 = input.nextInt();
        int y2 = input.nextInt();
        Complex C1 = new Complex(x1, y1);
        Complex C2 = new Complex(x2, y2);
        System.out.println("第一个复数：" + C1);
        System.out.println("第二个复数：" + C2);
        System.out.println("复数的和：" + C1.addComp(C2));
        System.out.println("复数的差：" + C1.subComp(C2));
        System.out.println("复数的积：" + C1.multiComp(C2));
        System.out.println("两个复数" + (C1.equalComp(C2) ? "相等" : "不等"));
    }
}

/**
 * 复数(z=x+iy)类
 *
 * @param x 实部
 * @param y 虚部
 */
record Complex(int x, int y) {
    /*显示复数*/
    @Override
    public String toString() {
        if (y >= 0)
            return x + "+" + y + "i";
        else
            return x + "-" + (-y) + "i";
    }

    /*复数相加*/
    public Complex addComp(Complex C2) {
        return new Complex(this.x + C2.x, this.y + C2.y);
    }

    /*复数相减*/
    public Complex subComp(Complex C2) {
        return new Complex(this.x - C2.x, this.y - C2.y);
    }

    /*复数相乘*/
    public Complex multiComp(Complex C2) {
        // 新复数实部=两复数实部乘积-两复数虚部乘积
        int newX = this.x * C2.x - this.y * C2.y;
        // 新复数虚部=两复数实部与虚部交叉乘积之和
        int newY = this.y * C2.x + this.x * C2.y;
        return new Complex(newX, newY);
    }

    /*判断两个复数是否相等*/
    public boolean equalComp(Complex C2) {
        return this.x == C2.x && this.y == C2.y;
    }
}