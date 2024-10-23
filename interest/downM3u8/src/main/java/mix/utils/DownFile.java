package mix.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DownFile {
    private static final List<ReqHead> reqHeadList = new ArrayList<>(); // 请求头集合

    /**
     * 从网络下载资源
     *
     * @param webUrl   网络链接
     * @param filePath 文件路径
     * @return 下载成功
     */
    public static boolean downFromWeb(String webUrl, String filePath) {
        try {
            URL url = new URL(webUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // 请求方法
            // 设置请求头
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            if (reqHeadList.size() > 0)
                for (ReqHead reqHead : reqHeadList)
                    conn.setRequestProperty(reqHead.key(), reqHead.value());
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
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void readReqConfig(String fileName) {
        try (InputStream fis = new FileInputStream(fileName)) {
            Properties props = new Properties();
            //加载属性列表
            props.load(fis);
            for (String key : props.stringPropertyNames())
                reqHeadList.add(new ReqHead(key, props.getProperty(key)));
        } catch (Exception ignored) {
        }
    }
}

record ReqHead(String key, String value) {
}