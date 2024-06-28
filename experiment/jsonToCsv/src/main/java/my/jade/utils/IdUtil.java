package my.jade.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdUtil {
    static int nowIndex = 1;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getId(1));
        }
    }

    public static long getId(int starID) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formatDateTime = starID + now.format(formatter) + (nowIndex++);
        return Long.parseLong(formatDateTime);
    }
}
