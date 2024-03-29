package course_01;

import java.util.Scanner;

/**
 * 【1.5】【题】公约公倍
 * 输入两个正整数m,n,求两个数的最大公约数和最小公倍数。
 *
 * @since 2022-3-10
 */
@SuppressWarnings("NonAsciiCharacters")
public class 公约公倍 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("输入第一个整数: ");
        int a = scan.nextInt();
        /*168和93*/
        System.out.println("输入第二个整数: ");
        int b = scan.nextInt();
        int i = a;
        int j = b;
        int x, y;
        if (a < b) {
            x = b;
            b = a;
            a = x;
        }
        while (b != 0) {
            y = a % b;
            a = b;
            b = y;
        }
        int t = i * j / a;
        System.out.println("最大公约数为：" + a);
        System.out.println("最小公倍数为：" + t);
    }
}