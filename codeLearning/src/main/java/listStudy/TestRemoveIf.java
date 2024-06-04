package listStudy;

import java.util.ArrayList;
import java.util.List;

/**
 * 从一个列表删除另一个列表中的元素，但使用removeIf
 *
 * @author MixJade
 * @since 2024-2-22 11:05:52
 */
public class TestRemoveIf {
    public static void main(String[] args) {
        // 首先是需要去重的列表
        List<OhYeh> ohYehList = new ArrayList<>();
        ohYehList.add(new OhYeh("A", "OK"));
        ohYehList.add(new OhYeh("B", "NO"));
        ohYehList.add(new OhYeh("C", "yeh"));
        ohYehList.add(new OhYeh("D", "aaa"));
        ohYehList.add(new OhYeh("E", "wc"));

        // 其次是去重的依据
        List<OhYeh> ohNoList = new ArrayList<>();
        ohNoList.add(new OhYeh("B", "NO"));
        ohNoList.add(new OhYeh("E", "wc"));

        // 使用removeIf去重(去除ohNoList所包含的)
        ohYehList.removeIf(ohNoList::contains);
        // 上面的代码等效于：
        // ohYehList.removeIf(item-> ohNoList.contains(item));

        // 也可以去除与之相等的，反正入参都是filter对象
        OhYeh dAaa = new OhYeh("D", "aaa");
        ohYehList.removeIf(dAaa::equals);

        // 输出去重的结果(去除列表的两个、单独的一个，只剩下两个)
        System.out.println(ohYehList);
    }

    record OhYeh(String name, String say) {
    }
}
