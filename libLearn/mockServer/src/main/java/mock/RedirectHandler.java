package mock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * 重定向处理器
 *
 * @since 2026-01-16 10:46:32
 */
public class RedirectHandler implements HttpHandler {
    // 目标URL（需指定完整的http/https协议）
    private final String targetUrl;

    public RedirectHandler(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 打印所有请求的关键信息（路径、方法）
        String requestPath = exchange.getRequestURI().getPath();
        String requestMethod = exchange.getRequestMethod();
        // 解析GET请求参数
        String queryParams = UrlUtil.parseGetParams(exchange.getRequestURI());

        // 打印参数信息
        long timestamp = System.currentTimeMillis(); // 可选：添加时间戳
        System.out.printf("""
                        \n[%d] 请求方法: %s, 路径: %s
                            GET参数: %s
                            重定向路径: %s
                        """,
                timestamp, requestMethod, requestPath, queryParams, targetUrl);

        // 设置Location响应头，指定目标转发地址
        exchange.getResponseHeaders().set("Location", targetUrl);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        // 设置3xx重定向状态码（302临时重定向，301永久重定向）
        exchange.sendResponseHeaders(302, -1); // 响应体长度为-1，表示无响应体
        // 添加CORS头
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        // 关闭交换流，释放资源
        exchange.close();
    }
}