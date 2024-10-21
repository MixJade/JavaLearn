package zhouYi;

/**
 * 生成64卦的标签
 *
 * @since 2024-10-21 16:48:06
 */
public class Gen64Gua {
    public static void main(String[] args) {
        // 乾三连，坤六断，震仰盂，艮覆碗，离中虚，坎中满，兑上缺，巽下断
        String[] ss = new String[]{
                "坤", "艮", "坎", "巽", "震", "离", "兑", "乾"
        };
        for (String s : ss) {
            for (String s1 : ss) {
                System.out.printf("<Gua name=\"%s%s\">%s%s的内容</Gua>%n", s, s1, s, s1);
            }
        }
    }
}