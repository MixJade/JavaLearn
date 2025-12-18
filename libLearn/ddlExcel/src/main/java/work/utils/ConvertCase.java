package work.utils;

/**
 * 将驼峰、蛇形的名称互相转换
 *
 * @since 2025-12-16 11:00:34
 */
public final class ConvertCase {

    /**
     * 将蛇形转为大驼峰
     *
     * @param input 蛇形，如:DOG_PLAN、dog_plan
     * @return 大驼峰，如:DogPlan
     */
    public static String snakeToLCamel(String input) {
        StringBuilder sb = new StringBuilder();
        String[] words = input.toLowerCase().split("_");
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
        }
        return sb.toString();
    }

    /**
     * 将蛇形转为小驼峰
     *
     * @param input 蛇形，如:DOG_PLAN、dog_plan
     * @return 小驼峰，如:dogPlan
     */
    public static String snakeToSCamel(String input) {
        String res = snakeToLCamel(input);
        // 首字母需要小写
        return res.substring(0, 1).toLowerCase() + res.substring(1);
    }
}
