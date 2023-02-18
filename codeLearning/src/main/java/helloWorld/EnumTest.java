package helloWorld;

public class EnumTest {
    public static void main(String[] args) {
        String day = EnumDemo.SUMMER.getDay();
        System.out.println(day);
    }
}

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