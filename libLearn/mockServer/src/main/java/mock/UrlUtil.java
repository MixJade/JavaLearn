package mock;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求参数解析工具
 *
 * @since 2026-01-08 17:19:55
 */
public final class UrlUtil {
    /**
     * 解析GET请求的查询参数
     */
    public static String parseGetParams(URI uri) {
        String query = uri.getQuery(); // 获取查询字符串（如 "username=test&password=123"）

        if (query == null || query.isEmpty()) {
            return "无";
        } else {
            return query;
        }
    }

    /**
     * 解析表单格式参数（application/x-www-form-urlencoded）
     */
    public static Map<String, String> parseFormParams(String formStr) {
        Map<String, String> params = new LinkedHashMap<>();
        String[] pairs = formStr.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8) : pair;
            String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8) : "";
            params.put(key, value);
        }
        return params;
    }

    /**
     * 读取请求体内容（核心方法）
     */
    public static String readRequestBody(HttpExchange exchange) throws IOException {
        // 获取请求体输入流，按UTF-8读取
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            return body.toString();
        }
    }
}
