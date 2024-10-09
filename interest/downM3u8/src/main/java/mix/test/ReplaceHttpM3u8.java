package mix.test;

import mix.utils.ReadTsFromM3u8;

import java.io.*;

/**
 * 替换掉http类型的m3u8中的链接
 */
public class ReplaceHttpM3u8 {
    public static void main(String[] args) {
        writeNewM3u8("example/index.m3u8", "example/newPlay.m3u8");
    }

    public static void writeNewM3u8(String m3u8Path, String newM3u8Path) {
        try (var reader = new BufferedReader(new FileReader(m3u8Path));
             var writer = new BufferedWriter(new FileWriter(newM3u8Path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 读取
                if (line.startsWith("http"))
                    line = ReadTsFromM3u8.getNameFromUrl(line);
                // 写入
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
