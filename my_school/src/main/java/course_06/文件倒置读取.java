package course_06;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * 【6.1】【题】文件倒置读取
 * 参照例10-8，使用RandomAccessFile流将一个文本文件倒置读出。
 *
 * @since 2022-5-11
 */
@SuppressWarnings("NonAsciiCharacters")
public class 文件倒置读取 {
    public static void main(String[] args) {
        File f = new File("src/main/resources/course_06/手榴弹.txt");
        // 打开文件
        try (RandomAccessFile randomFile = new RandomAccessFile(f, "rw")) {
            randomFile.seek(0);
            long m = randomFile.length();//m=文件长度
            while (m > 0) {
                m = m - 1;
                randomFile.seek(m);//定位到第m个字符
                byte c = randomFile.readByte(); // 这里读取出来的值不会超过255
                if (c >= 0) {//英文字符asc码小于255,大于0
                    System.out.print((char) c);
                } else {//汉字不在asc码范围内,
                    m = m - 2;//我的汉字占3个字节
                    randomFile.seek(m);
                    byte[] cc = new byte[3];
                    randomFile.readFully(cc);//cc为复制文件中连续的字节
                    System.out.print(new String(cc, StandardCharsets.UTF_8));//把cc转换为字符串
                }
            }
        } catch (Exception exp) {
            System.out.println("\n读取失败" + exp.getMessage());
        }
    }
}