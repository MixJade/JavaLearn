package course_01;

import java.util.Scanner;

/**
 * 【1.2】【题】成绩排序
 * 设计一个java程序，从键盘上输入某班课程的考试成绩（假设一个班有n名学生），
 * 然后根据分数按照从高到低的顺序重新排列，并统计不及格人数。
 *
 * @since 2022-3-10
 */
@SuppressWarnings("NonAsciiCharacters")
public class 成绩排序 {
    public static void main(String[] args) {
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.println("输入班级人数");
        n = sc.nextInt();
        if (n < 0) {
            System.out.println("应该输入一个正整数");
            System.exit(1);
        }
        int[] grade = new int[n];
        int counter = 0;
        Scanner scn = new Scanner(System.in);
        System.out.println("输入" + n + "位同学的成绩");
        for (int i = 0; i < n; i++) {
            grade[i] = scn.nextInt();
        }
        for (int i = 0; i < n; i++) {
            if (grade[i] < 60) {
                counter++;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (grade[j] < grade[j + 1]) {
                    int tem = grade[j + 1];
                    grade[j + 1] = grade[j];
                    grade[j] = tem;
                }
            }
        }
        System.out.println("不及格的人数: " + counter);
        System.out.println("成绩排序结果: ");
        for (int i = 0; i < n; i++) {
            System.out.print(grade[i] + " ");
        }
    }
}
