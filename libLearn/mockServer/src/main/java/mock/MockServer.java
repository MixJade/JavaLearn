package mock;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * 简易mock服务
 *
 * @since 2026-01-08 14:04:25
 */
public class MockServer {
    private final int port;
    private final String mockDir;

    public MockServer(int port, String mockDir) {
        this.port = port;
        this.mockDir = mockDir;
    }

    public void mountApi(ApiConf[] apiConfs) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            // 意外请求日志处理器
            server.createContext("/", new GlobalReqHandler());
            // 创建其它路由处理器
            for (ApiConf apiConf : apiConfs) {
                server.createContext(apiConf.path(), new MockHandler("src/main/resources/" + mockDir + "/" + apiConf.jsonName()));
            }
            // 设置线程池
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();

            System.out.println("Mock服务器启动成功，监听端口: " + port);
            for (ApiConf apiConf : apiConfs) {
                System.out.println("访问地址: http://localhost:" + port + apiConf.path());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}