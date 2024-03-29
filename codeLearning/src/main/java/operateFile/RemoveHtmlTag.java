package operateFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 去除html的尖括号
 *
 * @since 2024-1-2 19:47
 */
public class RemoveHtmlTag {
    public static void main(String[] args) {
        Path inputPath = Paths.get("src/main/resources/operateFile/去除html的尖括号.txt");
        Path outputPath = Paths.get("输出的文件/去除html的尖括号(结果).txt");

        try {
            String content = Files.readString(inputPath);
            // 使用正则表达式处理："<.*?>"匹配任何以尖括号包围的内容。
            content = content.replaceAll("<.*?>", "");

            // 匹配"(1)"这种括号包裹数字
            // content = content.replaceAll("\\(\\d{1,2}\\)", "");

            // 将处理过的内容写回到文件
            Files.writeString(outputPath, content);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}