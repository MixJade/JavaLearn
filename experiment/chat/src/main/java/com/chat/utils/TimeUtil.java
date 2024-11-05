package com.chat.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    /**
     * 返回当前时间
     *
     * @return 21:39:14
     */
    public static String nowTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return now.format(formatter);
    }
}
