package study;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 关于日期类的使用
 */
public class AttemptDate {
    public static void main(String[] args) {
        System.out.println("-------Begin-------");

        dateAttempt();
        formatTime();
        formatAnalysis();
        restrictTime("2022-08-09,04-00-00");
        restrictTime("2022-08-05,05-00-00");
        calenderAttempt();
    }

    private static void dateAttempt() {
        Date date1 = new Date();
        System.out.println(date1);

        long time1 = System.currentTimeMillis();
        System.out.println(time1);
        time1 += (60L * 24 * 60 * 60 * 1000);

        Date date2 = new Date();
        date2.setTime(time1);
        System.out.println(date2);
        System.out.println("--------------");
    }

    private static void formatTime() {
        Date date3 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日,hh时mm分ss秒 EEE a");
        String resultTime = sdf.format(date3);
        System.out.println(resultTime);
        System.out.println("--------------");
    }

    private static void formatAnalysis() {
        String strTime = "2022年08月05日,03:47:21";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日,hh:mm:ss");
        Date date4;
        try {
            date4 = sdf2.parse(strTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(date4);
        System.out.println(date4.getTime());
        Long resultTime2 = date4.getTime() + 60L * 24 * 60 * 60 * 1000;
        System.out.println(sdf2.format(resultTime2));
        System.out.println("----------------");
    }

    private static void restrictTime(String userTime) {
        String beginTime = "2022-08-05,04-00-00";
        String endTime = "2022-08-05,08-00-00";
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd,hh-mm-ss");
        Date beginTimeDate;
        Date endTimeDate;
        Date userTimeDate;
        try {
            beginTimeDate = sdf3.parse(beginTime);
            endTimeDate = sdf3.parse(endTime);
            userTimeDate = sdf3.parse(userTime);
            if (userTimeDate.before(beginTimeDate)) {
                System.out.println("You are so early!");
            }
            if (userTimeDate.after(endTimeDate)) {
                System.out.println("You are so lately!");
            } else {
                System.out.println("You are successful!!!");
            }
        } catch (ParseException e) {
            System.out.println("Time is not correct");
        }
        System.out.println("--------");
    }

    private static void calenderAttempt() {
        Calendar calendar1 = Calendar.getInstance();
        System.out.println(calendar1);
        int year1 = calendar1.getWeekYear();
        int year2 = calendar1.get(Calendar.YEAR);
        System.out.println("weekYear: " + year1 + " yieldYear: " + year2);

        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
        int day2 = calendar1.get(Calendar.DAY_OF_YEAR);
        int day3 = calendar1.get(Calendar.DAY_OF_WEEK);
        System.out.println("dayMonth: " + day1 + " dayYear: " + day2 + " dayWeek: " + day3);

        int month1 = calendar1.get(Calendar.MONTH) + 1;
        calendar1.add(Calendar.DAY_OF_YEAR, 60);
        int month2 = calendar1.get(Calendar.MONTH) + 1;
        System.out.println("The now month: " + month1 + " The change month:" + month2);
        Date date6 = calendar1.getTime();
        System.out.println(date6);
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy年-MM月-dd日,hh时mm分ss秒");
        System.out.println(sdf6.format(date6));
        System.out.println("------------------");
    }
}
