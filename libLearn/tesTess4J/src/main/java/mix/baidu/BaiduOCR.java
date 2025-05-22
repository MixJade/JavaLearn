package mix.baidu;

import mix.baidu.model.NormARes;
import mix.baidu.model.NormRes;
import mix.jade.JsonUtil;

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
import java.util.Base64;
import java.util.List;

/**
 * 通用文字识别
 */
public class BaiduOCR {
    public static void main(String[] args) {
        System.out.println(normalOCR("src/main/resources/tesImg/测试图片001.jpg"));
    }

    /**
     * 标准的OCR(识别拍照、截图的印刷体)
     *
     * @param imgPathStr 图片路径
     * @return 识别结果
     */
    public static String normalOCR(String imgPathStr) {
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
            Path tokenPath = Paths.get(ApiKey.TOKEN_FILE);
            if (Files.exists(tokenPath)) {
                // 文件存在
                List<String> lines = Files.readAllLines(tokenPath);
                String accessToken = lines.get(0);
                // 发起请求
                String result = post(url, accessToken, param);
                NormRes normRes = JsonUtil.strToObj(result, NormRes.class);
                StringBuilder resStr = new StringBuilder();
                if (normRes != null) {
                    for (NormARes aRes : normRes.words_result()) {
                        resStr.append(aRes.words()).append("\n");
                    }
                }
                return resStr.toString();
            } else {
                return "暂无token文件";
            }
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
    private static String post(String requestUrl, String accessToken, String params) {
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

            String result = response.body();
            System.err.println("请求结果：" + result);
            return result;
        } catch (Exception e) {
            return "Http请求错误";
        }
    }
}