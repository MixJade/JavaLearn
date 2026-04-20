package mock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class MockHandler implements HttpHandler {
    private final String jsonPath;

    public MockHandler(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 打印所有请求的关键信息（路径、方法）
        String requestPath = exchange.getRequestURI().getPath();
        String requestMethod = exchange.getRequestMethod();
        // 解析GET请求参数
        String queryParams = UrlUtil.parseGetParams(exchange.getRequestURI());

        // 解析POST/PUT请求体参数
        String bodyParamsStr = "无";
        if ("POST".equalsIgnoreCase(requestMethod) || "PUT".equalsIgnoreCase(requestMethod)) {
            String body = UrlUtil.readRequestBody(exchange);
            if (!body.isEmpty()) {
                // 根据Content-Type解析不同格式的请求体
                String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
                if (contentType != null && contentType.contains("application/x-www-form-urlencoded")) {
                    // 表单格式（key=value&key2=value2）
                    Map<String, String> formParams = UrlUtil.parseFormParams(body);
                    bodyParamsStr = formParams.toString();
                } else if (contentType != null && contentType.contains("application/json")) {
                    // JSON格式（直接打印原始JSON）
                    bodyParamsStr = body;
                } else {
                    // 其他格式（文本/纯字符串/二进制）
                    if (isBinaryString(body)) {
                        bodyParamsStr = "[二进制数据]";
                    } else {
                        bodyParamsStr = body;
                    }
                }
            }
        }

        // 打印参数信息
        long timestamp = System.currentTimeMillis(); // 可选：添加时间戳
        System.out.printf("""
                        \n[%d] 请求方法: %s, 路径: %s
                            GET参数: %s
                            Body参数: %s
                        """,
                timestamp, requestMethod, requestPath, queryParams, bodyParamsStr);

        try {
            // 读取JSON文件
            Path jsonPath = Paths.get(this.jsonPath);
            if (!Files.exists(jsonPath)) {
                sendResponse(exchange, 404, "{\"error\":\"JSON file not found\"}");
                return;
            }
            String jsonContent = Files.readString(jsonPath, StandardCharsets.UTF_8);
            if (jsonContent.contains("此处自动生成")) {
                jsonContent = jsonContent.replaceAll("此处自动生成", GenIdUtil.genId());
            }
            // 设置响应头
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            // 发送响应
            sendResponse(exchange, 200, jsonContent);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\":\"Internal Server Error: " + e.getMessage() + "\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    /**
     * 判断一个字符串是否是【二进制数据转成的乱码】
     *
     * @param str 要判断的字符串
     * @return true = 二进制乱码 | false = 正常文本
     */
    private static boolean isBinaryString(String str) {
        if (str == null || str.isEmpty()) return false;
        int nonAsciiCount = 0;
        int checkLen = Math.min(str.length(), 500);
        for (int i = 0; i < checkLen; i++) {
            char c = str.charAt(i);
            // 允许 ASCII 可打印字符、换行、制表符
            if (c < 32 && c != '\n' && c != '\r' && c != '\t') {
                return true; // 出现控制字符直接判定为二进制
            }
            if (c > 126) {
                nonAsciiCount++;
            }
        }
        // 如果非ASCII比例太高，可能是编码问题或二进制
        return nonAsciiCount * 100 / checkLen > 30;
    }
}