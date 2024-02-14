package trial;

import java.util.Calendar;
import java.util.Date;

public class TestCalendar {
    public static void main(String[] args) {
        // 查看15天后的date对象
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.add(Calendar.DATE, 15);
        expCalendar.set(Calendar.HOUR_OF_DAY, 0);
        expCalendar.set(Calendar.MINUTE, 0);
        expCalendar.set(Calendar.SECOND, 1);
        Date expTime = expCalendar.getTime();
        System.out.println(expTime);
    }
}
