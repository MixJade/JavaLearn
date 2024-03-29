package course_01;

/**
 * 【1.4】【题】分数序列和
 * 有一个分数序列
 * 2/1,3/2,5/3,8/5,13/8,21/13......
 * 求出这个数列的前20项之和。
 *
 * @since 2022-3-10
 */
@SuppressWarnings("NonAsciiCharacters")
public class 分数序列和 {
    public static void main(String[] args) {
        int a = 2, b = 1;
        int c;
        double sum = 0;
        for (int i = 1; i <= 20; i++) {
            sum += (double) a / b;
            c = b;
            b = a;
            a = b + c;
        }
        System.out.println("前20项之和为：" + sum);
    }
}