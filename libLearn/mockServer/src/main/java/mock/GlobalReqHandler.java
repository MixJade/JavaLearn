package mock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 全局意外请求的url打印
 * <p>注册到根路径，这样其它匹配不上的请求都会</p>
 */
public class GlobalReqHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        // 打印所有请求的关键信息（路径、方法）
        String requestPath = exchange.getRequestURI().getPath();
        String requestMethod = exchange.getRequestMethod();
        long timestamp = System.currentTimeMillis(); // 可选：添加时间戳
        System.out.printf("\n[%d] 意外请求 -  请求方法: %s, 路径: %s%n",
                timestamp, requestMethod, requestPath);
        // 关闭当前exchange，交给对应处理器处理
        exchange.close();
    }
}