package correspondence;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Java下载网络图片
 *
 * @since 2024-10-09 16:47:18
 */
public class DownPicture {
    public static void main(String[] args) {
        String imageUrl = "https://pic.netbian.com/uploads/allimg/240811/194847-17233769273ebe.jpg";
        String destinationFile = "downloaded.jpg";

        // 下载图片并保存
        saveImage(imageUrl, destinationFile);

        System.out.println("Image saved to " + destinationFile);
    }

    private static void saveImage(String imageUrl, String destinationFile) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // 请求方法
            // 设置请求头
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();
            try (InputStream is = conn.getInputStream();
                 OutputStream os = new FileOutputStream(destinationFile)) {
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