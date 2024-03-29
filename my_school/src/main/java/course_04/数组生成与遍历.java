package course_04;

import java.util.Arrays;
import java.util.Random;

/**
 * 【4.2】【题】数组生成与遍历
 * 随机产生某个班级、某门课程的考试成绩（0-100），
 * 然后按照考试分数从高到低的顺序重新排列，并显示统计不及格人数。
 *
 * @since 2022-4-20
 */
@SuppressWarnings("NonAsciiCharacters")
class 数组生成与遍历 {
    public static void main(String[] args) {
        int maxCount = 10; // 总共10人
        int[] x = new int[maxCount];
        int i;
        System.out.println("这" + maxCount + "个人的成绩是：");
        Random rd = new Random();
        for (i = 0; i < maxCount; i++) {
            x[i] = rd.nextInt(100);
            System.out.print(x[i] + ", ");
        }
        System.out.println("\n成绩从高到低排列为：");
        Arrays.sort(x);
        // 逆向遍历
        for (int j = x.length; j-- > 0; ) {
            System.out.print(x[j] + ", ");
        }
        // 统计不及格人数
        int failNum = 0;
        for (int j = 0; j < 10; j++) {
            if (x[j] < 60) failNum++;
        }
        System.out.println("\n不及格的人数有" + failNum + "人\n");
    }
}