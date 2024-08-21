package correspondence;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 简易Http请求
 *
 * @apiNote 运行前请先运行SimpleServer.java
 * @since 2024-08-21 11:16:03
 */
public class SimpleHttpReq {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        // 创建链接请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8001/test"))
                .build();
        // 异步
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
        // 同步
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
