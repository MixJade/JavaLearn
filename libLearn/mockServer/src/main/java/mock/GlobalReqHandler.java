package mock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 全局意外请求的url打印
 * <p>注册到根路径，这样其它匹配不上的请求都会</p>
 */
public class GlobalReqHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        // 打印所有请求的关键信息（路径、方法）
        String requestPath = exchange.getRequestURI().getPath();

        // 处理 favicon.ico 请求，返回图标文件
        if ("/favicon.ico".equals(requestPath)) {
            try (exchange) {
                byte[] icoData = Files.readAllBytes(Path.of("src/main/resources/favicon.ico"));
                exchange.getResponseHeaders().set("Content-Type", "image/x-icon");
                // 设置缓存头（浏览器缓存7天，避免重复请求）
                exchange.getResponseHeaders().set("Cache-Control", "max-age=604800");
                // 返回图标文件
                exchange.sendResponseHeaders(200, icoData.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(icoData);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            String requestMethod = exchange.getRequestMethod();
            long timestamp = System.currentTimeMillis(); // 可选：添加时间戳
            System.out.printf("\n[%d] 意外请求 -  请求方法: %s, 路径: %s%n",
                    timestamp, requestMethod, requestPath);
            // 关闭当前exchange
            exchange.close();
        }
    }
}