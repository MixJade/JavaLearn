package com.demo.service.impl;

import com.demo.model.baidu.AuthToken;
import com.demo.model.baidu.NormARes;
import com.demo.model.baidu.NormRes;
import com.demo.service.OcrService;
import com.demo.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * 百度的Ocr识别服务
 *
 * @since 2025-06-11 10:51:14
 */
@Service
public class BaiduOcrServiceImpl implements OcrService {
    private static final Logger log = LoggerFactory.getLogger(BaiduOcrServiceImpl.class);
    private static String token;
    @Value("${prep.apiKey}")
    private String apiKey;
    @Value("${prep.secretKey}")
    private String secretKey;

    @Override
    public String normalOCR(String imgPathStr) {
        log.info("识别图片:{}", imgPathStr);
        try {
            // 请求url
            String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";

            // 本地文件路径
            Path imgPath = Paths.get(imgPathStr);
            if (!Files.exists(imgPath))
                return "文件未找到";
            // 将图片转为Base64再转为url编码
            byte[] imgData = Files.readAllBytes(imgPath);
            String imgStr = Base64.getEncoder().encodeToString(imgData);
            String imgParam = URLEncoder.encode(imgStr, StandardCharsets.UTF_8);
            // 发请求
            String param = "image=" + imgParam;
            if (token == null) token = getToken();
            // 发起请求
            String result = post(url, token, param);
            if (result.contains("error_code"))
                return "无效Token";
            NormRes normRes = JsonUtil.strToObj(result, NormRes.class);
            StringBuilder resStr = new StringBuilder();
            if (normRes != null) {
                for (NormARes aRes : normRes.words_result()) {
                    resStr.append(aRes.words()).append("\n");
                }
            }
            return resStr.toString();
        } catch (IOException e) {
            return "解析失败";
        }
    }

    /**
     * 发起Post表单请求
     *
     * @param requestUrl  请求URL
     * @param accessToken 授权Token
     * @param params      请求参数
     * @return 响应结果
     */
    private String post(String requestUrl, String accessToken, String params) {
        try {
            String encoding = "UTF-8";
            if (requestUrl.contains("nlp")) {
                encoding = "GBK";
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl + "?access_token=" + accessToken))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(params.getBytes(encoding)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // log.info("请求结果：" + response.body());
            return response.body();
        } catch (Exception e) {
            return "Http请求错误";
        }
    }

    private String getToken() {
        log.info("从百度获取token__{}", LocalDateTime.now());
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        String requestUrl = "https://aip.baidubce.com/oauth/2.0/token?client_id=" + apiKey
                + "&client_secret=" + secretKey
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

            log.info("HTTP状态码: " + statusCode);

            if (statusCode == 200) {
                AuthToken authToken = JsonUtil.strToObj(responseBody, AuthToken.class);
                if (authToken != null) {
                    token = authToken.access_token();
                }
            } else {
                log.error("请求失败，状态码: " + statusCode + "，响应: " + responseBody);
            }

        } catch (IOException | InterruptedException e) {
            log.error("百度鉴权接口调用失败: " + e.getMessage());
            Thread.currentThread().interrupt(); // 恢复中断状态
        }

        return token;
    }
}
