package mix.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownFile {
    /**
     * 从网络下载资源
     *
     * @param webUrl   网络链接
     * @param filePath 文件路径
     */
    public static void downFromWeb(String webUrl, String filePath) {
        try {
            URL url = new URL(webUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // 请求方法
            // 设置请求头
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();
            try (InputStream is = conn.getInputStream();
                 OutputStream os = new FileOutputStream(filePath)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }
            }
            conn.disconnect();
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }
}