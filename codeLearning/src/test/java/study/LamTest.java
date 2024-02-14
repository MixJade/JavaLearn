package study;

import java.util.function.Function;

/**
 * 测试双冒号引用实例方法，感觉好废
 * <p>其实这种是将方法以参数的形式引用</p>
 * <p>这种形式常见于第三方框架，如MybatisPlus</p>
 *
 * @since 2024-1-5 20:05
 */
public class LamTest {
    public static void main(String[] args) {
        // 这是单纯的实例化调用，比较鸡肋
        TheLam theLam = new TheLam("你好");
        Function<TheLam, String> getStr = TheLam::str;
        String str = getStr.apply(theLam); // 通过apply+对象来触发
        System.out.println(str);
        // 这是类型相同但对象不同，通过入参调用不同方法
        System.out.println("=========第二种形式========");
        TheLam theLam2 = new TheLam("两行琉璃");
        diffMethod(TheLam::str, theLam2);
        diffMethod(TheLam::secondMethod, theLam2);
    }

    /**
     * 通过入参，调用TheLam对象的不同方法
     *
     * @param getStr TheLam对象中，返回值为String的方法
     * @param theLam TheLam对象
     */
    private static void diffMethod(Function<TheLam, String> getStr, TheLam theLam) {
        System.out.println("鸟说：" + getStr.apply(theLam));
    }
}

record TheLam(String str) {
    String secondMethod() {
        System.out.println(str + "的第二法启动");
        return str + "的第二法";
    }
}
