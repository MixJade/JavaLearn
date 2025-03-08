package com.chat.utils;

import java.util.Random;

public class ColorUtil {
    /**
     * 输出随机颜色(限定深色)
     *
     * @return 颜色的hex编码
     */
    public static String randomColor() {
        Random random = new Random();
        StringBuilder randomColor = new StringBuilder("#");
        for (int i = 0; i < 3; i++) {
            // 深色的三个rgb值都小于128
            String hexString = Integer.toHexString(random.nextInt(128));
            if (hexString.length() < 2) hexString = "0" + hexString;
            randomColor.append(hexString);
        }
        return String.valueOf(randomColor);
    }
}
