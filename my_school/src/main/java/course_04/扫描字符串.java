package course_04;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 【4.1】【题】
 * 使用Scanner类的实例解析字符串“数学87分，物理76分，英语96分”中的考试成绩，并计算出总成绩以及平均成绩。
 *
 * @since 2022-4-20
 */
@SuppressWarnings("NonAsciiCharacters")
public class 扫描字符串 {
    public static void main(String[] args) {
        String cost = " 数学 87 分，物理 76 分，英语 96 分";
        Scanner scanner = new Scanner(cost);
        scanner.useDelimiter("[^0123456789.]+");
        double sum = 0;
        int count = 0;
        while (scanner.hasNext()) {
            try {
                double score = scanner.nextDouble();
                count++;
                sum = sum + score;
                System.out.println(score);
            } catch (InputMismatchException exp) {
                System.out.println("扫描失败");
            }
        }
        System.out.println("总分:" + sum + "分");
        System.out.println("平均分:" + sum / count + "分");
    }
}