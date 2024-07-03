package listStudy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 测试对象列表根据某一属性值排序
 * <p>学习排序器Comparator的使用</p>
 *
 * @author MixJade
 * @since 2024-6-20 17:08:18
 */
public class TestSortCompare {
    static List<Man> getManList() {
        List<Man> manList = new ArrayList<>();
        manList.add(new Man("法外狂徒张三", 12));
        manList.add(new Man("李四", 23));
        manList.add(new Man("王五", 5));
        manList.add(new Man("小白", 82));
        manList.add(new Man("老黑", 16));
        manList.add(new Man("无名", null));
        manList.add(new Man("白鸽", 23));
        manList.add(new Man("兰博", 62));
        manList.add(new Man("马保国", 69));
        manList.add(new Man("张san", 12));
        manList.add(new Man("李si", 23));
        manList.add(new Man("T800", 19));
        manList.add(new Man("T801", 18));
        manList.add(new Man("T803", 17));
        manList.add(new Man("T804", 16));
        manList.add(new Man("T805", 15));
        return manList;
    }

    public static void main(String[] args) {
        List<Man> manList = getManList();
        // 引入排序器(根据年龄排序,null会排到最后)
        Comparator<Man> comparator = Comparator.comparing(Man::age, Comparator.nullsLast(Comparator.naturalOrder()));
        // 开始排序
        manList.sort(comparator);
        // 查看结果
        manList.forEach(System.out::println);
    }
}

record Man(String name, Integer age) {
}
