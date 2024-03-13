package trial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试StringJoin语法
 *
 * @since 2024-3-13 09:48:39
 */
public class TestStringJoin {
    public static void main(String[] args) {
        // 1. 将数组按照指定分隔符弄成字符串
        String[] strArray = {"张三", "李四", "王五"};
        String oneString = String.join(",", strArray);
        System.out.println(oneString);
        // 2. 也可以弄列表
        List<String> stringList = new ArrayList<>(Arrays.asList(strArray));
        String twoString = String.join(" ", stringList);
        System.out.println(twoString);

    }
}
