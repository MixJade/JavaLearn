package course_06;

import java.io.*;

/**
 * 【6.2】【题】读文件并写行号
 * 参照例10-7，使用Java的输入、输出流将一个文本文件的内容按行读出，每读出一行就顺序添加行号，并写入到另一个文件中。
 *
 * @since 2022-5-11
 */
@SuppressWarnings("NonAsciiCharacters")
public class 读文件并写行号 {
    public static void main(String[] args) {
        File f1 = new File("src/main/java/course_06/文件倒置读取.java");
        File f2 = new File("输出的文件/读文件并写行号的输出结果.txt");//自动新建，不用提前命名
        // BufferedReader和BufferedWriter关闭时会关闭创建时使用的输入/输出流
        try (BufferedReader br = new BufferedReader(new FileReader(f1));
             BufferedWriter bw = new BufferedWriter(new FileWriter(f2))) {
            String temp;
            int i = 1;
            while ((temp = br.readLine()) != null) {
                bw.write("【" + i + "】 " + temp);
                bw.newLine();//换行
                i++;
            }
            bw.flush();//把缓冲区内容写到文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
