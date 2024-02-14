package leetCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 力扣入门题：两数之和
 * <p>给定一个数组和一个目标和，从数组中找两个数字,使之相加等于目标和</p>
 * <p>输出这两个数字的下标</p>
 */
public class TwoNumberSum {
    public static void main(String[] args) {
        System.out.println("---两数之和，返回下标---");
        int[] resultFirst = twoNumberSum(new int[]{2, 5, 7, 6, 3}, 9);
        int[] resultSecond = twoNumberSum(new int[]{2, 3, 4, 5, 6}, 8);
        System.out.println(Arrays.toString(resultFirst) + "\n" + Arrays.toString(resultSecond));
    }

    private static int[] twoNumberSum(int[] number, int target) {
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
