package mix.baidu;

import mix.baidu.model.AuthToken;
import mix.jade.JsonUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BaiduTokenGet {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        String requestUrl = "https://aip.baidubce.com/oauth/2.0/token?client_id=" + ApiKey.API_KEY
                + "&client_secret=" + ApiKey.SECRET_KEY
                + "&grant_type=client_credentials";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            // 同步发送请求
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            String responseBody = response.body();

            System.out.println("HTTP状态码: " + statusCode);

            if (statusCode == 200) {
                AuthToken authToken = JsonUtil.strToObj(responseBody, AuthToken.class);
                if (authToken != null) {
                    writeToken(authToken.access_token());
                }
            } else {
                System.err.println("请求失败，状态码: " + statusCode + "，响应: " + responseBody);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("百度鉴权接口调用失败: " + e.getMessage());
            Thread.currentThread().interrupt(); // 恢复中断状态
        }
    }

    private static void writeToken(String token) {
        File file = new File(ApiKey.TOKEN_FILE);
        try {
            // 创建新文件
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(token);
            System.out.println("Token文件写入成功");
            writer.close();
        } catch (IOException ignored) {
        }
    }
}