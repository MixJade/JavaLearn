package leetCode;

import java.util.Stack;

/**
 * 42. 接雨水
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * @since 2024/1/22 11:22
 */
class HoldRain {
    public static void main(String[] args) {
        // 案例1：由数组[0,1,0,2,1,0,1,3,2,1,2,1]表示的高度图，可接6单位雨水
        int result1 = trap6(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        System.out.println("案例1可接" + result1 + "单位雨水");
        // 案例2：由数组[4,2,0,3,2,5]表示的高度图，可接9单位雨水
        int result2 = trap6(new int[]{4, 2, 0, 3, 2, 5});
        System.out.println("案例2可接" + result2 + "单位雨水");
    }

    /**
     * 接雨水:栈解法
     *
     * @param height 柱子的高度数组
     * @return int 可接多少单位雨水
     * @since 2024/1/22 11:22
     */
    static int trap6(int[] height) {
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        int current = 0; // 指针
        // 双层while遍历
        // 但是考虑到每个元素最多访问两次，入栈一次和出栈一次，所以时间复杂度是 O（n）。
        // 空间复杂度：O（n）。栈的空间。
        while (current < height.length) {
            //如果栈不空并且当前指向的高度大于栈顶高度就一直循环
            while (!stack.empty() && height[current] > height[stack.peek()]) {
                int h = height[stack.peek()]; //取出要出栈的元素
                stack.pop(); //出栈
                if (stack.empty()) { // 栈空就出去
                    break;
                }
                int distance = current - stack.peek() - 1; //两堵墙之间的距离。
                int min = Math.min(height[stack.peek()], height[current]);
                sum = sum + distance * (min - h);
            }
            stack.push(current); //当前指向的编号(墙的x坐标)入栈
            current++; //指针后移
        }
        return sum;
    }
}