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
                new ApiConf("/xxx", "xxx.json"),
        });
    }
}
