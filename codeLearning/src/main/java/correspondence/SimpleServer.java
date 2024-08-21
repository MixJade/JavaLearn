package correspondence;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * Java简易服务器
 *
 * @apiNote 访问：<a href="http://localhost:8001/test">测试</a>输出“hello world”
 * @since 2024-08-21 11:00:47
 */
public class SimpleServer {
    public static void main(String[] arg) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
            server.createContext("/test", exchange -> {
                String response = "hello world";
                exchange.sendResponseHeaders(200, 0);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            });
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
