package mix.baidu;

import mix.baidu.model.AuthToken;
import mix.jade.JsonUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BaiduAuthRequest {
    public static void main(String[] args) {
        try {
            // 创建URL对象
            URL url = new URL("https://aip.baidubce.com/oauth/2.0/token?client_id=" + ApiKey.API_KEY + "&client_secret=" + ApiKey.SECRET_KEY + "&grant_type=client_credentials");

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法和头信息
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // 启用输出流（即使请求体为空）
            connection.setDoOutput(true);

            // 发送请求
            try (OutputStream ignored = connection.getOutputStream()) {
                // 此处请求体为空，直接关闭输出流
            }

            // 读取响应
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // 输出响应
            System.out.println("HTTP状态码: " + responseCode);
            // System.out.println("响应内容: " + response);
            AuthToken authToken = JsonUtil.strToObj(response.toString(), AuthToken.class);
            if (authToken != null) {
                writeToken(authToken.access_token());
            }
            // 关闭连接
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("百度鉴权接口报错");
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