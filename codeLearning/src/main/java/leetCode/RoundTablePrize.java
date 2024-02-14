package leetCode;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 圆桌奖品分配-头条2019笔试题
 * <p>有n人比赛，比赛后每人都得到不同分数；现所有人排成一圈领取奖品，要求：</p>
 * <p>1、相邻两人中得分高的人得到的奖品比得分低的人多；</p>
 * <p>2、每个人至少得到一个奖品；</p>
 * <p>(注意：座位是固定的，输入分数的顺序就是座位的顺序)</p>
 * <p>问：最少应该准备多少个奖品?</p>
 *
 * @since 2024-1-8 22:18
 */
public class RoundTablePrize {
    static int[] prize, scores; // 奖品数量、分数
    static int menNum; // 人数

    /**
     * 获取第x位人的奖品数
     *
     * @param x 人的索引
     * @return 奖品数量
     */
    static int getPrizeNum(int x) {
        if (prize[x] != 0) return prize[x]; // 已经有奖品的就不要掺和
        // 最重要的他前面人和后面人
        int beforeMan = (x + menNum - 1) % menNum; // 前面的人(%menNum是为了循环数组)
        int afterMan = (x + 1) % menNum; // 后面的人
        // 如果前面人的得分大于此人
        boolean theBeforeBig = scores[beforeMan] >= scores[x];
        // 且后面人的得分也大于此人
        boolean theAfterBig = scores[afterMan] >= scores[x];
        // 那么这个人就是最低分，只能拿一个奖品
        if (theBeforeBig && theAfterBig)
            return prize[x] = 1;
        int v = 0;
        // 如果前面人的得分不如此人，那么就获取前面人的奖品数
        if (scores[beforeMan] < scores[x])
            v = getPrizeNum(beforeMan);
        // 如果后面人的得分不如此人，那么就获取后面人的奖品数
        if (scores[afterMan] < scores[x])
            v = Math.max(v, getPrizeNum(afterMan));
        // 此人的奖品数是前面和后面的奖品数中，最多的那个+1
        // (且将这个结果保存到奖品数量列表)
        return prize[x] = v + 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 记忆搜索法
        System.out.println("请输入人数：(如：8)");
        menNum = sc.nextInt(); // 人数
        scores = new int[menNum]; // 每个人所得的分数
        prize = new int[menNum]; // 每个人所得的奖品
        System.out.println("请输入这" + menNum + "个人的分数，空格分隔。(如：1 3 5 7 4 2 6 8)");
        for (int i = 0; i < menNum; i++)
            scores[i] = sc.nextInt();
        // 给每个人计算奖品
        for (int i = 0; i < menNum; i++)
            getPrizeNum(i);
        // 将奖品数量加起来
        int res = 0;
        for (int i = 0; i < menNum; i++)
            res = res + prize[i];
        System.out.println("至少需要" + res + "个奖品");
        System.out.println("每个人的奖品：" + Arrays.toString(prize));
    }
}