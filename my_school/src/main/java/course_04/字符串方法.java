package course_04;

/**
 * 【4.3】【题】字符串方法
 * 将一批单词存入一个字符串数组中，例如：{"good","word","work","mean","thank","me","you","or","and"}
 * 进行如下处理：
 * 1) 统计含有子字符串or的单词个数；
 * 2) 统计以字符m开头的单词个数。
 *
 * @since 2022-4-20
 */
@SuppressWarnings("NonAsciiCharacters")
public class 字符串方法 {
    public static void main(String[] args) {
        String[] st = {"good", "word", "work", "mean", "thank", "me", "you", "or", "and"};
        int cor = 0, cm = 0;
        for (String s : st) {
            if (s.contains("or"))
                cor++;
            if (s.startsWith("m"))
                cm++;
        }
        System.out.println("or共有" + cor + "个，m开头的单词有" + cm + "个");
    }
}