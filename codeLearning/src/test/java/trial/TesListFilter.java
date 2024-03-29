package trial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 列表去重测试
 * <p>从一个列表删除另一个列表中的元素</p>
 */
public class TesListFilter {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>(Arrays.asList("零", "一", "二", "三", "四"));
        System.out.println(stringList);
        System.out.println(testMove(stringList));
    }

    private static List<String> testMove(List<String> stringList) {
        List<String> dbList = new ArrayList<>(Arrays.asList("零", "二"));
        return stringList.stream()
                .filter(i -> !dbList.contains(i))
                .toList();
    }
}
