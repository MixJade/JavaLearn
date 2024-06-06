package trial;

import java.util.*;

/**
 * 测试Java的重写equals方法运用
 *
 * @since 2024-6-6 20:49:36
 */
public class TestEqualsMethod {
    public static void main(String[] args) {
        Dog dog1 = new Dog(1L, "Tom");
        Dog dog2 = new Dog(2L, "Tom");
        Dog dog3 = new Dog(1L, "马保国");

        // 1 测试重写了equals方法的比较
        System.out.println("=====测试1======");
        System.out.println("不同ID但同名：" + dog1.equals(dog2));
        System.out.println("不同名但同ID：" + dog1.equals(dog3));

        // 2 测试列表的包含方法
        System.out.println("\n=====测试2======");
        List<Dog> dogList = new ArrayList<>();
        dogList.add(dog1);
        dogList.add(dog2);
        // 进行contains比较
        System.out.println("dogList(无dog3)会包含dog3吗：" + dogList.contains(dog3));

        // 3 测试Map的get方法
        System.out.println("\n=====测试3======");
        Map<Dog, Integer> dogMap = new HashMap<>();
        dogList.add(dog3);
        for (Dog dog : dogList) {
            System.out.println(dog);
            if (!dogMap.containsKey(dog)) {
                dogMap.put(dog, 1);
            } else {
                dogMap.put(dog, dogMap.get(dog) + 1);
            }
        }
        // 最后的map值
        System.out.println("dogMap最终值：" + dogMap);

        // 4 测试异种比较
        System.out.println("\n=====测试4======");
        System.out.println("但不同类型不会相同：" + dog2.equals(2L));
        System.out.println("同类型就行：" + dog2.equals(new Dog(2L, "怡中")));

        // 5 测试异种取值
        System.out.println("\n=====测试5======");
        Map<Object, Integer> dogMap2 = new HashMap<>();
        dogMap2.put(dog1, 1);
        System.out.println("objMap能通过Long取值吗：" + dogMap2.get(1L));
    }
}

/**
 * 重写了equals方法的Java类
 *
 * @param dogId 主键，比较时只比较这个
 * @param name  名字，为了做区分
 */
record Dog(Long dogId, String name) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return Objects.equals(dogId, dog.dogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dogId);
    }
}