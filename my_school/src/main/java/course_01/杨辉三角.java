package course_01;

import java.util.Scanner;

/**
 * 【1.1】【题】杨辉三角
 * 打印指定行数的杨辉三角，从键盘输入行数。（使用Scanner类）
 *
 * @since 2022-3-10
 */
@SuppressWarnings("NonAsciiCharacters")
public class 杨辉三角 {
    public static void main(String[] args) {
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入行数：");
        n = sc.nextInt();
        if (n < 0) {
            System.out.println("要输入一个正整数");
            System.exit(1);
        }
        int[][] yh = new int[n][n];
        int i, j;
        //把第一列和最后一列的数改成1
        for (i = 0; i < yh.length; i++) {
            for (j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    yh[i][j] = 1;
                } else {
                    yh[i][j] = yh[i - 1][j - 1] + yh[i - 1][j];
                }
            }
        }
        //输出杨辉三角形
        for (i = 0; i < yh.length; i++) {
            for (j = 0; j <= i; j++) {
                System.out.print(yh[i][j] + " ");
            }
            System.out.println();
        }
    }
}