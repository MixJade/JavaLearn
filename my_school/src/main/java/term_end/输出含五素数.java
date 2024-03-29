package term_end;

/**
 * 1. 编写一程序，输出从100到1000包含数字5的全部素数。
 *
 * @since 2022-6-13
 */
@SuppressWarnings("NonAsciiCharacters")
public class 输出含五素数 {
    public static void main(String[] args) {
        int i, j, a, b, c;
        for (i = 100; i <= 1000; i++) {
            for (j = 2; j < i; j++) {
                if (i % j == 0)
                    break;
                if (j == i - 1) {
                    a = i / 100;
                    b = i / 10 % 10;
                    c = i % 10;
                    if (a == 5 || b == 5 || c == 5)
                        System.out.println(i);
                    break;
                }
            }
        }
    }
}