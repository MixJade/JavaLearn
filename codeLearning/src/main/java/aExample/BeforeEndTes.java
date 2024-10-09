package aExample;

/**
 * 测试在关闭程序之前，输出一句话
 *
 * @since 2024-10-09 22:16:07
 */
public class BeforeEndTes {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("程序结束了！")));
        System.out.println("接下来程序会执行50秒，请提前关闭它");
        // 让主线程运行一段时间
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}