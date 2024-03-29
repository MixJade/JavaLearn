package leetCode;

/**
 * 给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。 如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
 */
class NumberOfSteps {
    public static void main(String[] args) {
        NumberOfSteps steps = new NumberOfSteps();
        int step = steps.numberOfSteps(14);
        System.out.println(step);
    }

    public int numberOfSteps(int num) {
        int step = 0;
        while (num != 0) {
            if (num % 2 == 0) {
                num /= 2;
            } else {
                num--;
            }
            step++;
        }
        return step;
    }
}