package course_01;

/**
 * 【1.6】【题】
 * 数组A：{1，7，9，11，13，15，17，19}
 * 数组B：{2，4，6，8，10}
 * 将两个数组A,B合并为数组C，按升序排列。
 *
 * @since 2022-3-10
 */
@SuppressWarnings("NonAsciiCharacters")
public class 数组合并 {
    public static void main(String[] args) {
        int[] A = {1, 7, 9, 11, 13, 15, 17, 19};
        int[] B = {2, 4, 6, 8, 10};
        printArray(merge(A, B));

    }

    public static int[] merge(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < A.length && j < B.length) {
            if (A[i] < B[j]) {
                C[k++] = A[i++];
            } else {
                C[k++] = B[j++];
            }
        }
        while (i < A.length) {
            C[k++] = A[i++];
        }
        while (j < B.length) {
            C[k++] = B[j++];
        }
        return C;
    }

    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}