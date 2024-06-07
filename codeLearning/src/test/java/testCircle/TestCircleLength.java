package testCircle;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试计数器与列表长度的关系
 */
public class TestCircleLength {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("1");
        stringList.add("1");
        stringList.add("1");
        stringList.add("1");
        stringList.add("1");
        int stringNum = 0;
        for (String s : stringList) {
            if ("1".equals(s)) {
                stringNum++;
            }
        }
        System.out.println("计数器：" + stringNum);
        System.out.println("列表长度：" + stringList.size());
        System.out.println(stringNum == stringList.size());
    }
}
