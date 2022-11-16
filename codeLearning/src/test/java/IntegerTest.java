import org.junit.Test;

public class IntegerTest {
    @Test
    public void testInteger() {
        Integer a = 100, b = 100, c = 150, d = 150;
        System.out.println("数据在-128与127之间，直接用字面量：" + (a == b));
        System.out.println("数据超过这个范围，就是类似String的引用地址了：" + (c == d));
        changeInteger(a);
        System.out.println("依然是值传递:" + a);
    }

    private void changeInteger(Integer integer) {
        integer = 200;
    }

    @Test
    public void testGroup() {
        //引用传递
        System.out.println("下面的是值传递，但是传的是引用");
        int[] group = {2, 3, 4};
        changeGroup(group);
        for (int i : group) {
            System.out.print(i + " ");
        }
    }

    private void changeGroup(int[] ins) {
        for (int i = 0; i < ins.length; i++) {
            ins[i] = 9;
        }
    }

    @Test
    public void testString() {
        //同一个值的String
        String str01 = "hhh相同值，相同的地址";
        String str02 = "hhh相同值，相同的地址";
        String str03 = "hhh相同值" + "，相同的地址"; // 会被编译器自动合并
        String str04 = "hhh相同值";
        String str05 = "，相同的地址";
        String str06 = str04 + str05;
        String str07=new String("hhh相同值，相同的地址");
        System.out.println(str01 == str02);
        System.out.println(str03 == str01);
        System.out.println(str06 == str01);
        System.out.println(str06.equals(str01));
        System.out.println(str07==str01);
    }
}
