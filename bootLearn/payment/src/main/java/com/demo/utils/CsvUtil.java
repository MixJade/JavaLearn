package com.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 专门处理csv文件的工具类
 *
 * @since 2025-08-05 10:29:27
 */
public final class CsvUtil {
    /**
     * 读取csv文件的所有行
     *
     * @param file
     * @return 所有行数的列表
     */
    public static List<String> readCsvFile(MultipartFile file) {
        List<String> lines = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * 解析csv的一行
     *
     * @param line csv的一行
     * @return 每行的元素
     */
    public static List<String> parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        boolean escapeNextQuote = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (escapeNextQuote) {
                // 处理转义双引号（两个双引号转义为一个）
                currentField.append('"');
                escapeNextQuote = false;
                continue;
            }

            if (c == '"') {
                if (inQuotes) {
                    // 检查下一个字符是否也是双引号（转义）
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        escapeNextQuote = true;
                        i++; // 跳过下一个字符
                    } else inQuotes = false;
                } else inQuotes = true;
            } else if (c == ',' && !inQuotes) {
                // 分割字段
                fields.add(currentField.toString().trim());
                currentField.setLength(0);
            } else currentField.append(c);
        }

        // 添加最后一个字段
        fields.add(currentField.toString().trim());
        return fields;
    }
}
