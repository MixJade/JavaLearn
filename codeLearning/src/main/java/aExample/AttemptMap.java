package aExample;

import java.util.HashMap;
import java.util.Map;

public class AttemptMap {
    public static void main(String[] args) {
        System.out.println("---关于Map的新手教程---");
        mapAttempt();
    }

    private static void mapAttempt() {
        String[] firstName = {"ladder", "cupboard", "direct", "sacre", "bearing"};
        String[] secondName = {"梯子", "碗柜", "直达的", "恐慌", "方向"};
        Map<String, String> stringMap = new HashMap<>();
        for (int i = 0; i < firstName.length; i++) {
            stringMap.put(firstName[i], secondName[i]);
        }
        System.out.println("直接输出整个Map:" + stringMap);
        System.out.println("查找是否有这个键，返回布尔:" + stringMap.containsKey("bearing"));
        System.out.println("根据键查找值:" + stringMap.get(firstName[2]));
        System.out.println("展示所有值(很明显存储是无序的):" + stringMap.values());
        System.out.println("根据键查找值(但有默认值):" + stringMap.getOrDefault("noName", "默认值"));
    }
}
