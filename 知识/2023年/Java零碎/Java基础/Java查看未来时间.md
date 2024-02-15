# Java查看未来时间

```java
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

```

这段Java代码的作用是创建一个表示未来时间的`Date` 对象。

\- `Calendar.getInstance()` 创建一个默认时区和语言环境的`Calendar`实例。

\- `expCalendar.add(Calendar.DATE, 15);` 使用 `add()` 方法实现日期的加减运算，这里是将当前日期加15天。

\- `expCalendar.set(Calendar.HOUR_OF_DAY, 0);`、`expCalendar.set(Calendar.MINUTE, 0);`、`expCalendar.set(Calendar.SECOND, 1);` 这三行代码是设置时间为00:00:01。

\- `Date expTime = expCalendar.getTime();` 最后使用 `getTime()` 方法获取 `Calendar` 对象所表示的时间，并将其转为 `Date` 对象。

所以`expTime`表示的时间就是从现在开始，未来15天后的凌晨00:00:01的那一个时间点。