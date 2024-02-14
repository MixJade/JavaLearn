package trial;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试将一个List分割成多个
 *
 * @since 2023/7/26 12:12
 */
public class TestCutList {
    public static void main(String[] args) {
        // 进行初始化
        List<JJ> jjList = new ArrayList<>();
        String[] jjType = {"特大", "大", "中", "较小", "特别小", "韩国"};
        for (int i = 0; i < 33; i++) {
            jjList.add(new JJ(i, jjType[i % jjType.length]));
        }
        // 计算分成多少组
        int groupSize = 10;
        int numGroup = jjList.size() / groupSize + 1;
        System.out.printf("能够分成%s组\n", numGroup);
        // 开始分割
        List<List<JJ>> resList = new ArrayList<>(numGroup);
        for (int i = 0; i < numGroup; i++) {
            resList.add(jjList.subList(i * groupSize, Math.min((i + 1) * groupSize, jjList.size())));
        }
        resList.forEach(System.out::println);
    }

    record JJ(int ind, String type) {
    }
}

