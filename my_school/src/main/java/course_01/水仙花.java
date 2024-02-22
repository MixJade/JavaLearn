package course_01;

/**
 * 【1.3】【题】水仙花
 * 输出所有的“水仙花数” ， 所谓“水仙花数” 是指一个3位数，其各位数字立方和等于该数本身。如153.
 *
 * @since 2022-3-10
 */
@SuppressWarnings("NonAsciiCharacters")
public class 水仙花 {
    public static void main(String[] args) {
        System.out.println("水仙花是：");
        int i;
        for (i = 100; i < 1000; i++) {
            int bw, sw, gw;//百十个位
            bw = i / 100;
            sw = i / 10 % 10;
            gw = i % 10;
            if ((Math.pow(bw, 3) + Math.pow(sw, 3) + Math.pow(gw, 3)) == i)
                System.out.println(i);
        }
    }
}