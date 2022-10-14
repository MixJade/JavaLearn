package regulationCategory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AttemptMap {
    public static void main(String[] args) {
        System.out.println("---关于Map的新手教程---");
        mapAttempt();
        System.out.println("---两数之和，返回下标---");
        int[] resultFirst = twoNumberSum(new int[]{2, 5, 7, 6, 3}, 9);
        int[] resultSecond = twoNumberSum(new int[]{2,3,4,5,6},8);
        System.out.println(Arrays.toString(resultFirst)+"\n"+ Arrays.toString(resultSecond));
    }

    private static void mapAttempt() {
        String[] firstName = {"ladder", "cupboard", "direct", "sacre", "bearing"};
        String[] secondName = {"梯子", "碗柜", "直达的", "恐慌", "方向"};
        Map<String, String> stringMap = new HashMap<>();
        for (int i = 0; i < firstName.length; i++) {
            stringMap.put(firstName[i], secondName[i]);
        }
        System.out.println("直接输出整个Map:"+stringMap);
        System.out.println("查找是否有这个键，返回布尔:"+stringMap.containsKey("bearing"));
        System.out.println("根据键查找值:"+stringMap.get(firstName[2]));
        System.out.println("展示所有值(很明显存储是无序的):"+stringMap.values());
    }

    private static int[] twoNumberSum(int[] number, int target) {
        //给定一个数组和一个目标和，从数组中找两个数字相加等于目标和，输出这两个数字的下标
        int i, j, needNumber;
        int[] resultNumber = new int[2];
        Map<Integer, Integer> twoSumMap = new HashMap<>();
        for (i = 0; i < number.length; i++) {
            twoSumMap.put(number[i], i);
        }
        for (j = 0; j < number.length; j++) {
            needNumber = target - number[j];
            if (twoSumMap.containsKey(needNumber) && twoSumMap.get(needNumber) != j) {
                resultNumber[0] = twoSumMap.get(needNumber);
                resultNumber[1] = j;
                return resultNumber;
            }
        }
        return resultNumber;
    }
}
