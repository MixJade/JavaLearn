package work.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * 将String生成为文件
 */
public class StrToFile {
    /**
     * Files工具类生成文件
     *
     * @param content  要写入的字符串内容
     * @param fileName 输出的文件路径
     */
    public static void toFile(String content, String fileName) {
        Path filePath = Path.of("生成结果", fileName);
        toFilePath(content, filePath);
    }

    /**
     * Files工具类生成文件
     *
     * @param content  要写入的字符串内容
     * @param filePath 输出的文件路径
     */
    public static void toFilePath(String content, Path filePath) {
        try {
            // 检测并创建目录
            Files.createDirectories(filePath.getParent());

            // 文件写入选项：CREATE：文件不存在则创建、WRITE：写入权限、TRUNCATE_EXISTING：覆盖模式
            StandardOpenOption[] options = new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING};

            // 写入文件
            Files.writeString(
                    filePath,  // 转换为 Path 对象
                    content,  // 字符串转字节（指定编码避免乱码）
                    StandardCharsets.UTF_8,
                    options);
            System.out.println("文件：【" + filePath + "】已成功生成");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
