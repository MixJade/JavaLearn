package operateFile;

import java.io.*;
import java.util.Base64;
import java.util.Objects;

public class JpgToBase64 {
    public static void main(String[] args) {
        String pathStr = "src/main/resources/testResize.jpg";
        imageToBase64(pathStr);
    }

    private static void imageToBase64(String pathStr) {
        byte[] data = readImage(pathStr);
        // 匹配绝对路径的正则表达式：pathStr.replaceAll(".+\\\\", "")
        // 以下：匹配相对路径的正则表达式（最终结果是无后缀的文件名）
        String resultFileName = (pathStr.replaceAll(".+/", "")).replaceAll("\\..+", "");
        String resultFilePath = "输出的文件/" + resultFileName + "（图片）的base64.txt";
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        // 返回Base64编码过的字节数组字符串
        String theJpgBase64 = encoder.encodeToString(Objects.requireNonNull(data));
        try {
            FileOutputStream export = new FileOutputStream(resultFilePath);
            byte[] exportAttend = (theJpgBase64).getBytes();
            export.write(exportAttend);
            export.close();
            System.out.println("文件成功写入！");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            System.out.println("你这文件有问题啊");
        } catch (IOException e2) {
            System.out.println("文件没有写入");
        }
    }

    private static byte[] readImage(String methodPath) {
        byte[] data = null;
        InputStream in = null;
        try {
            in = new FileInputStream(methodPath);
            data = new byte[in.available()];
            long howLong = in.read(data);
            System.out.println("图片字节长度: " + howLong);
            in.close();
        } catch (IOException e) {
            System.out.println("没有找到图片");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

}