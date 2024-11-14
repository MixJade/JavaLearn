package study;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * 测试GBK与UTF8互转
 */
public class TranGbkUtf8 {
    public static void main(String[] args) {
        tranGbkToUtf8("测试");
        tranGbkToUtf8("测试师");
    }

    /**
     * 将字符串从utf8转为gbk，再从gbk转回utf8
     *
     * @param test 正常的utf8字符串
     */
    private static void tranGbkToUtf8(String test) {
        try {
            boolean isAlone = false; // 当前字符长度是奇数
            // 注：当test长度为奇数时会导致末尾字符串乱码
            if (test.length() % 2 == 1) {
                isAlone = true;
                test += "s";
            }
            String testGbkUtf8 = new String(test.getBytes(StandardCharsets.UTF_8), "GBK");
            System.out.println(testGbkUtf8);//娴嬭瘯
            String testUtf8Gbk = new String(testGbkUtf8.getBytes("GBK"), StandardCharsets.UTF_8);
            if (isAlone) {
                // 长度为奇数时的末尾字符串给去掉
                testUtf8Gbk = testUtf8Gbk.substring(0, testUtf8Gbk.length() - 1);
            }
            System.out.println(testUtf8Gbk);//测试
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
