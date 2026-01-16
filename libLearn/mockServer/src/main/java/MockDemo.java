import mock.ApiConf;
import mock.MockServer;

/**
 * 简易mock样例
 *
 * @since 2026-01-08 14:25:50
 */
public class MockDemo {
    public static void main(String[] args) {
        MockServer mockServer = new MockServer(18021, "mock");
        mockServer.mountApi(new ApiConf[]{
                new ApiConf("/login", "user.json"),
                // 这里的path都是上下文，也就是说，访问/xxx/view也可获取xxx.json
                new ApiConf("/xxx", "xxx.json"),
                new ApiConf("/baidu", "https://www.baidu.com"),
        });
    }
}
