package mix.baidu;

import mix.baidu.model.NormARes;
import mix.baidu.model.NormRes;
import mix.baidu.utils.Base64Util;
import mix.baidu.utils.FileUtil;
import mix.baidu.utils.HttpUtil;
import mix.jade.JsonUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 通用文字识别
 */
public class GeneralBasic {
    public static String generalBasic() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        try {
            // 本地文件路径
            String filePath = "src/main/resources/tesImg/测试图片001.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, StandardCharsets.UTF_8);

            String param = "image=" + imgParam;
            Path tokenPath = Paths.get(ApiKey.TOKEN_FILE);
            if (Files.exists(tokenPath)) {
                // 文件存在
                List<String> lines = Files.readAllLines(tokenPath);
                String accessToken = lines.get(0);
                System.out.println(accessToken);
                // 发起请求
                String result = HttpUtil.post(url, accessToken, param);
                NormRes normRes = JsonUtil.strToObj(result, NormRes.class);
                StringBuilder resStr = new StringBuilder();
                if (normRes != null) {
                    for (NormARes aRes : normRes.words_result()) {
                        resStr.append(aRes.words()).append("\n");
                    }
                }
                return resStr.toString();
            } else {
                System.err.println("暂无token");
                return "暂无token";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(generalBasic());
    }
}