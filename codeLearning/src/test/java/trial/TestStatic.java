package trial;

/**
 * 调用静态方法，不用新建对象
 */
public class TestStatic {
    public static void main(String[] args) {
        QuietNoNew.quietMethod();
    }
}

class QuietNoNew {
    public static void quietMethod() {
        System.out.println("调用静态方法，不用新建对象。\n===========");
    }
}