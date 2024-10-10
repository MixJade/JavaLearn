package mix.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownFile {
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

    /**
     * 写下错误日志
     *
     * @param errMsg 下载失败文件的路径
     */
    public static void writeErrorText(String errMsg) {
        final String errFileName = "错误日志.txt";
        File errFile = new File(errFileName);
        if (!errFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                errFile.createNewFile();
            } catch (IOException ignored) {
            }
        }
        try (FileWriter fw = new FileWriter(errFileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(errMsg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}