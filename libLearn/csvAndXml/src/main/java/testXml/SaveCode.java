package testXml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一键保存代码
 *
 * @since 2023-6-25
 */
public class SaveCode {
    private final File OUTPUT_PATH = new File("output/代码保存结果.md");
    private final Map<String, FileManager> NAME_MAP = new HashMap<>();

    /**
     * 构造方法，参数是输出文件的路径
     */
    public SaveCode() {
        makeNameMap();
        verifyCode();
    }

    public static void main(String[] args) {
        SaveCode saveCode = new SaveCode();
        saveCode.saveMixJadeCode("src");
    }

    /**
     * 读取xml中的信息,
     * 返回文件名与文件详情的集合
     */
    private void makeNameMap() {
        SAXReader saxReader = new SAXReader();
        InputStream is = getClass().getResourceAsStream("SaveCodeDictionary.xml");
        try {
            Document document = saxReader.read(is);
            List<Node> fileManagers = document.selectNodes("/FileGather/FileManager");
            for (Node fileManager : fileManagers) {
                Element mixFile = (Element) fileManager;
                String fileName = mixFile.element("FileName").getTextTrim();//这个方法意思是去掉空格获取文本
                String fileCnName = mixFile.element("FileCnName").getTextTrim();
                String introduce = mixFile.element("Introduce").getTextTrim();
                NAME_MAP.put(fileName, new FileManager(fileCnName, introduce));
            }
        } catch (DocumentException e) {
            System.out.println("xml路径有问题");
        }
    }

    /**
     * 写入md文件的开头，同时清空之前的内容
     */
    private void verifyCode() {
        String altTime = new SimpleDateFormat("MM月dd日 a HH:mm").format(new Date());
        try (var fw = new FileWriter(OUTPUT_PATH)) {
            fw.write("# 散玉的JAVA代码\n>创建时间:" + altTime + "\n>在暑假学习JAVA过程中所编辑的代码，为了能够在未来派上用场，特此建立一个md文件来记录。\n---\n");
        } catch (IOException e) {
            System.out.println("目录写入失败");
        }
    }

    /**
     * 读取文件目录，将里面文件的内容写入md文件
     */
    private void saveMixJadeCode(String codeWarehouse) {
        File f = new File(codeWarehouse);
        String endText = ("\n```\n</details>\n\n---\n");
        int i = 1;
        if (!f.exists()) {
            System.out.println(codeWarehouse + " not exists");
            return;
        }
        File[] fileName = f.listFiles();
        assert fileName != null;
        try (var bw01 = new BufferedWriter(new FileWriter(OUTPUT_PATH, StandardCharsets.UTF_8, true))) {
            for (File fs : fileName) {
                BasicFileAttributes attributes = Files.readAttributes(fs.toPath(), BasicFileAttributes.class);
                if (attributes.isRegularFile()) {
                    String reallyName = fs.getName();
                    String endSuffix = reallyName.replaceAll(".+\\.", "");
                    String secondText, tempt;
                    String beginText = "***文件夹:" + codeWarehouse + "***\n";
                    bw01.write(beginText);
                    if (NAME_MAP.get(reallyName) != null) {
                        secondText = ("## " + i++ + "、" + NAME_MAP.get(reallyName).fileCnName() + "\n > " + NAME_MAP.get(reallyName).introduce() + " \n\n<details> \n<summary>" + reallyName + "</summary>\n\n```" + endSuffix + "\n");
                    } else {
                        System.out.println(reallyName);
                        secondText = ("## " + i++ + "、" + reallyName + "\n > 暂时没有简介 \n\n<details> \n<summary>" + reallyName + "</summary>\n\n```" + endSuffix + "\n");
                    }
                    var br01 = new BufferedReader(new FileReader(fs, StandardCharsets.UTF_8));
                    bw01.write(secondText);
                    while ((tempt = br01.readLine()) != null) {
                        bw01.write(tempt);
                        bw01.newLine();
                    }
                    br01.close();
                    bw01.write(endText);
                }
                if (attributes.isDirectory()) {
                    saveMixJadeCode(fs.getPath());
                }
            }
            bw01.flush();
        } catch (Exception e) {
            System.out.println("读取文件出错" + e);
        }
    }
}

/**
 * 文件信息
 *
 * @param fileCnName 文件中文名
 * @param introduce  简介
 */
record FileManager(String fileCnName, String introduce) {
}
