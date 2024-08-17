package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Java执行Python
 *
 * @since 2024-8-17 21:35:49
 */
public class JavaExcPython {
    public static void main(String[] args) {
        try {
            String pythonDir = "../../PythonLearn/Normal/utils/";
            ProcessBuilder pb = new ProcessBuilder("python", pythonDir + "生成时间戳.py");
            Process process = pb.start();
            // 输出脚本的输出
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
