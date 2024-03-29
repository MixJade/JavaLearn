package operateFile;

import java.io.*;

/**
 * 将txt大文件，通过其中的分割字符串给分成多个文件
 *
 * @since 2024-1-10 20:54
 */
public class SpiltTxtFile {
    // 输入文件
    private static final String INPUT_FILE = "src/main/resources/operateFile/通过字符串分割.txt";
    // 输出文件的路径与文件前缀
    private static final String OUTPUT_FILE_PREFIX = "输出的文件/分割后的文件";
    // 用于分割的字符串
    private static final String DELIMITER = "分割线";

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            int count = 1;
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PREFIX + count + ".txt"));
            while ((line = reader.readLine()) != null) {
                if (DELIMITER.equals(line.trim())) {
                    writer.close();
                    count++;
                    writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PREFIX + count + ".txt"));
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException exc) {
            System.out.println("I/O Error: " + exc);
        }
    }
}