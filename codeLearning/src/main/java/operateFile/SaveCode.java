package operateFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveCode {
    public static void main(String[] args) {
        SaveCode saveCode = new SaveCode("C:\\MixJade\\zooMarkdown\\代码保存结果.md");
        saveCode.saveMixJadeCode("src");
    }

    private final File RESULT_PATH;
    private final Map<String, ArrayList<String>> NAME_MAP = new HashMap<>();

    /**
     * 构造方法，参数是输出文件的路径
     */
    public SaveCode(String RESULT_PATH) {
        this.RESULT_PATH = new File(RESULT_PATH);
        makeNameMap();
        verifyCode();
    }

    /**
     * 读取xml中的信息,
     * 返回文件名与文件详情的集合
     */
    private void makeNameMap() {
        String strXml = " ";
        try {
            String xml_path = "src/main/resources/operateFile/SaveCodeDictionary.xml";
            strXml = Files.readString(Path.of(xml_path));
        } catch (IOException e) {
            System.out.println("XML不见了" + e);
        }

        Pattern p1 = Pattern.compile("(?<=<FileName>).+(?=</FileName>)");
        Pattern p2 = Pattern.compile("(?<=<FileChineseName>).+(?=</FileChineseName>)");
        Pattern p3 = Pattern.compile("(?<=<Introduce>).+(?=</Introduce>)");
        Matcher m1 = p1.matcher(strXml);
        Matcher m2 = p2.matcher(strXml);
        Matcher m3 = p3.matcher(strXml);
        while (m1.find() && m2.find() && m3.find()) {
            ArrayList<String> followList = new ArrayList<>();
            followList.add(m2.group());
            followList.add(m3.group());
            NAME_MAP.put(m1.group(), followList);
        }
    }

    /**
     * 写入md文件的开头，同时清空之前的内容
     */
    private void verifyCode() {
        String altTime = new SimpleDateFormat("MM月dd日 a HH:mm").format(new Date());
        try {
            FileWriter fw = new FileWriter(RESULT_PATH);
            fw.write("# 散玉的JAVA代码\n>创建时间:" + altTime + "\n>在暑假学习JAVA过程中所编辑的代码，为了能够在未来派上用场，特此建立一个md文件来记录。\n---\n");
            fw.close();
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
        try {
            BufferedWriter bw01 = new BufferedWriter(new FileWriter(RESULT_PATH, StandardCharsets.UTF_8, true));
            for (File fs : fileName) {
                BasicFileAttributes attributes = Files.readAttributes(fs.toPath(), BasicFileAttributes.class);
                if (attributes.isRegularFile()) {
                    String reallyName = fs.getName();
                    String endSuffix = reallyName.replaceAll(".+\\.", "");
                    String secondText, tempt;
                    String beginText = "***文件夹:" + codeWarehouse + "***\n";
                    bw01.write(beginText);
                    if (NAME_MAP.get(reallyName) != null) {
                        secondText = ("## " + i++ + "、" + NAME_MAP.get(reallyName).get(0) + "\n > " + NAME_MAP.get(reallyName).get(1) + " \n\n<details> \n<summary>" + reallyName + "</summary>\n\n```" + endSuffix + "\n");
                    } else {
                        System.out.println(reallyName);
                        secondText = ("## " + i++ + "、" + reallyName + "\n > 暂时没有简介 \n\n<details> \n<summary>" + reallyName + "</summary>\n\n```" + endSuffix + "\n");
                    }
                    BufferedReader br01 = new BufferedReader(new FileReader(fs, StandardCharsets.UTF_8));
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
            bw01.close();
        } catch (Exception e) {
            System.out.println("读取文件出错" + e);
        }
    }
}
