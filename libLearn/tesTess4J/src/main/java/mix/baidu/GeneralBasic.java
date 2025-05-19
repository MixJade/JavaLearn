package mix.baidu;

import mix.baidu.utils.Base64Util;
import mix.baidu.utils.FileUtil;
import mix.baidu.utils.HttpUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "[access_token]";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        GeneralBasic.generalBasic();
    }
}