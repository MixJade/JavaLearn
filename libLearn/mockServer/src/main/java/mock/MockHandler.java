package mock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MockHandler implements HttpHandler {
    private final String jsonPath;

    public MockHandler(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // 读取JSON文件
            Path jsonPath = Paths.get(this.jsonPath);
            if (!Files.exists(jsonPath)) {
                sendResponse(exchange, 404, "{\"error\":\"JSON file not found\"}");
                return;
            }
            String jsonContent = Files.readString(jsonPath, StandardCharsets.UTF_8);
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
}