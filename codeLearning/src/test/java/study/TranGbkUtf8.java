package study;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * 测试GBK与UTF8互转
 */
public class TranGbkUtf8 {
    public static void main(String[] args) {
        try {
            String test = "测试";
            String testGbkUtf8 = new String(test.getBytes(StandardCharsets.UTF_8), "GBK");
            System.out.println(testGbkUtf8);//娴嬭瘯
            String testUtf8Gbk = new String(testGbkUtf8.getBytes("GBK"), StandardCharsets.UTF_8);
            System.out.println(testUtf8Gbk);//测试
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
