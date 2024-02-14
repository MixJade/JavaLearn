package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 八皇后问题
 *
 * @since 2024-1-5 21:32
 */
public class EightQueen {
    static int count = 0;

    /**
     * 要求在 8 × 8 的棋盘上摆放8个皇后，使”皇后“们不能互相攻击
     * <p>当任意两个皇后都不处于同一行、同一列或同一条斜线上时就不会相互攻击，即为目标解</p>。
     */
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            nums.add(i);
        }
        permute(new ArrayList<>(), nums);
        System.out.println("一种有" + count + "种方法");
    }

    /**
     * 递归方法
     *
     * @param selected   最终结果列
     * @param candidates 剩余的小球
     */
    private static void permute(List<Integer> selected, List<Integer> candidates) {
        // 小球为空，说明找完了
        if (candidates.size() == 0) {
            System.out.println(selected);
            count++;
            return;
        }
        // 固定当前小球，遍历下一个小球
        for (int i = 0; i < candidates.size(); i++) {
            Integer select = candidates.get(i); // 获取当前小球的数字
            if (noBias(selected, select)) {
                selected.add(select); // 当前小球添加至结果列
                candidates.remove(i); // 从剩余小球移除选择的小球
                permute(selected, candidates); // 以固定当前小球的情况，进入下一次循环
                candidates.add(i, select);
                selected.remove(selected.size() - 1); // 进入下一条不选择这个小球的世界线
            }
        }
    }

    /**
     * 判定当前小球是否与已经存在的小球在同一斜线
     *
     * @param selected 已存在的小球
     * @param nowNum   当前小球的数字
     * @return 不在斜线上为真
     */
    private static boolean noBias(List<Integer> selected, Integer nowNum) {
        int nowIndex = selected.size() + 1; // 当前小球的位置号:已存在球的数量+1
        for (int i = 0; i < selected.size(); i++) {
            // 同一斜线：|x1-x2|=|y1-y2|
            if (Math.abs(selected.get(i) - nowNum) == Math.abs((i + 1) - nowIndex))
                return false;
        }
        return true;
    }
}
