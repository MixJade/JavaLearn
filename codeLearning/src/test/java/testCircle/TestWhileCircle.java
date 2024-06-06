package testCircle;

import org.junit.Test;

public class TestWhileCircle {
    @Test
    public void testWhile() {
        // 当判断为false时,不进入while,故可省下一个if
        int i = 0;
        while (i != 0) {
            System.out.println("当判断为false时,while不执行");
            // 以防万一
            i++;
            if (i > 2) break;
        }
        System.out.println("END");
    }

    @Test
    public void testDoWhile() {
        // 但do-while会执行一次
        int i = 0;
        do {
            System.out.printf("i=%s %s\n", i, i != 0);
            if (i == 0) {
                System.out.println("当判断为false时,do-while会执行一次");
            }
            // 以防万一
            i++;
            if (i > 2) break;
        } while (i != 0);
        System.out.println("END");
    }
}
