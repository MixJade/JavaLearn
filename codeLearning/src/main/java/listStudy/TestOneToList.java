package listStudy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 测试将一个对象转为List
 *
 * @since 2024/3/15 18:30
 */
public class TestOneToList {
    public static void main(String[] args) {
        Dog dog = new Dog("旺财");
        List<Dog> list = Collections.singletonList(dog);
        System.out.println("使用工具类转化(不可变):" + list);
        /*
         singletonList转成的对象是不可变的，比如这里就会报错
         list.add(new Dog("来福"));
         System.out.println("不可变对象(会报错):" + list);
        */
        List<Dog> list2 = new ArrayList<>();
        list2.add(dog);
        list2.add(new Dog("来福"));
        System.out.println("新建List追加(可变):" + list2);
    }

    record Dog(String name) {
    }
}
