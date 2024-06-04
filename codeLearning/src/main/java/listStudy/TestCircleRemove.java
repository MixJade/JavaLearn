package listStudy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试边遍历边删除
 */
public class TestCircleRemove {
    @Test
    public void testRemove() {
        String[] strNed = {"零", "一", "二", "三"};
        List<String> stringList = new ArrayList<>(Arrays.asList(strNed));
        System.out.println(stringList);
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).equals("零") || stringList.get(i).equals("三")) {
                stringList.remove(i);
                i--;
            }
        }
        System.out.println(stringList);
    }
}
