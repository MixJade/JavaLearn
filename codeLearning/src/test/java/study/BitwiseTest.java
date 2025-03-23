package study;

/**
 * 位运算测试
 *
 * @since 2025-03-23 10:03:58
 */
public class BitwiseTest {
    public static void main(String[] args) {
        System.out.println("2的9次方 : " + (2 << 9) + " 预期:1024");
        System.out.println("2的19次方: " + (2 << 19) + " 预期:" + (1024 * 1024));
        System.out.println("2的29次方: " + (2 << 29) + " 预期:" + (1024 * 1024 * 1024));
        // 开始计算
        System.out.println("10240除1024: " + (10240 >> 10) + " 预期:10");
        // 位运算保留小数
        // 其余mb单位
        long length = 4090608;
        long intPart = length >> 20; //整数部分
        long decPart = (length - (intPart << 20)) * 100 >> 20; // 小数部分(x100保留两位小数)
        System.out.println("4090608除1024*1024的整数部分: " + intPart);
        System.out.println("4090608除1024*1024的小数部分: " + decPart);
        System.out.println(intPart + "." + decPart + "MB");
    }
}
