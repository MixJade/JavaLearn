package trial;

enum EnumDemo {
    SPRING("春天"), SUMMER("夏天"), AUTUMN("秋天"), WINTER("冬天");
    private final String day;

    EnumDemo(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}

/**
 * 枚举使用测试
 */
public class TestEnum {
    public static void main(String[] args) {
        String day = EnumDemo.SUMMER.getDay();
        System.out.println(day);
    }
}