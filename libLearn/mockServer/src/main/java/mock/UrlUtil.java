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
    private static Map<String, String> parseFormParams(String formStr) {
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

    private static String readReqBodyStr(HttpExchange exchange) throws IOException {
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

    /**
     * 简单判断字符串是否为合法 JSON（对象或数组）
     */
    private static boolean isJson(String str) {
        if (str == null || str.isEmpty()) return false;
        String trimmed = str.trim();
        return (trimmed.startsWith("{") && trimmed.endsWith("}"))
                || (trimmed.startsWith("[") && trimmed.endsWith("]"));
    }

    /**
     * 读取请求体内容（核心方法）
     */
    public static String readRequestBody(HttpExchange exchange) throws IOException {
        String body = readReqBodyStr(exchange);
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        if (!body.isEmpty()) {
            // 尝试解码为字符串
            if (UrlUtil.isJson(body)) {
                // JSON 格式：直接展示
                return body;
            } else if (contentType != null && contentType.contains("application/x-www-form-urlencoded")) {
                // 表单格式（key=value&key2=value2）
                Map<String, String> formParams = parseFormParams(body);
                return formParams.toString();
            } else {
                // 非 JSON 非表单：可能是二进制或其他格式
                return "[非JSON数据]";
            }
        }
        return "NULL";
    }
}
