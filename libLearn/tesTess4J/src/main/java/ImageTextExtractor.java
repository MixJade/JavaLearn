import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageTextExtractor {
    public static String extractTextFromImage(File imageFile, String dataPath) {
        ITesseract tesseract = new Tesseract();
        try {
            // 设置Tesseract数据路径（包含语言数据的目录）
            tesseract.setDatapath(dataPath);
            // 设置语言为中文和英文
            tesseract.setLanguage("chi_sim+eng");
            // 中文识别优化参数
            tesseract.setVariable("chop_enable", "T");
            tesseract.setVariable("use_new_state_cost", "F");
            tesseract.setVariable("segment_segcost_rating", "F");
            tesseract.setVariable("enable_new_segsearch", "0");
            // 执行OCR
            return tesseract.doOCR(imageFile);
        } catch (Exception e) {
            System.err.println("出错了: " + e.getMessage());
            return null;
        }
    }

    public static Map<String, String> batchProcessImages(String folderPath, String dataPath) {
        Map<String, String> results = new HashMap<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file.getName())) {
                    String text = extractTextFromImage(file, dataPath);
                    if (text != null) {
                        results.put(file.getName(), text);
                    }
                }
            }
        }
        return results;
    }

    private static boolean isImageFile(String fileName) {
        String lowerCaseName = fileName.toLowerCase();
        return lowerCaseName.endsWith(".png") || lowerCaseName.endsWith(".jpg") ||
                lowerCaseName.endsWith(".jpeg");
    }

    public static void main(String[] args) {
        String folderPath = "src/main/resources/tesImg";
        // Tesseract语言数据路径，需要下载中文和英文数据
        String dataPath = "src/main/resources/tessdata";
        Map<String, String> results = batchProcessImages(folderPath, dataPath);
        System.out.println("开始测试:" + results.entrySet().size());
        for (Map.Entry<String, String> entry : results.entrySet()) {
            System.out.println("图片: " + entry.getKey());
            System.out.println("解析文字:\n" + entry.getValue() + "\n" + "=".repeat(50));
        }
    }
}