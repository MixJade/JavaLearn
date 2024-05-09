package study;

import org.junit.Test;

/**
 * 测试使用break跳出两层循环
 *
 * @author MixJade
 * @since 2024-5-9 14:45:51
 */
public class TestBreakTwoFor {
    public static void main(String[] args) {
        outer:
        for (int i = 0; i < 5; i++) {
            System.out.println("i=" + i);
            for (int j = 10; j < 20; j++) {
                System.out.println("j=" + j);
                if (j == 15)
                    break outer; // 跳到标签位置
            }
        }
    }

    /**
     * 首先，双层循环里面的break只能跳出一层循环
     */
    @Test
    public void justBreakOne() {
        for (int i = 0; i < 3; i++) {
            System.out.println("=====第" + i + "次外层循环=====");
            for (int j = 0; j < 5; j++) {
                System.out.println("===第" + i + "次循环:" + j + "===");
                if (j == 3)
                    break;
            }
        }
    }

    /**
     * 但使用标签可以直接跳出所有循环
     * <p>标签主要用于终止循环语句</p>
     */
    @Test
    public void outBreakTwo() {
        myLabel:
        for (int i = 0; i < 3; i++) {
            System.out.println("=====第" + i + "次外层循环=====");
            for (int j = 0; j < 5; j++) {
                System.out.println("===第" + i + "次循环:" + j + "===");
                if (j == 3)
                    break myLabel;
            }
        }
    }
}