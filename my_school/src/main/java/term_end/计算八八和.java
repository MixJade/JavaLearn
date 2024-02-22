package term_end;

/**
 * 2. 编写源程序，定义一个类Sum8，用循环控制语句编写程序计算8+88+888+8888+…的前10项和。
 *
 * @since 2022-6-13
 */
@SuppressWarnings("NonAsciiCharacters")
public class 计算八八和 {
    public static void main(String[] args) {
        int a;
        long b = 0;
        long c = 0;
        System.out.println("计算8+88+888...前10项之和");
        for (a = 1; a <= 10; a++) {
            b = b * 10 + 8;
            c = c + b;
        }
        System.out.println(c);
    }
}