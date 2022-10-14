import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveCode02 {
    String cloth;

    SaveCode02(String cloth) {
        this.cloth = cloth;
        tideNote();
    }


    private void tideNote() {
        String cite = "# Mybatis笔记\n>创建时间: 2022年9月16日21点 \n>第一个mybatis项目文件备份。\n---\n";
        String wrap = "\n---\n## Mybatis文件备份\n---\n";
        try {
            String note = Files.readString(Path.of("Mybatis学习笔记.md"));
            FileWriter fileAttempt = new FileWriter(cloth);
            fileAttempt.write(cite + note + wrap);
            fileAttempt.close();
        } catch (IOException e) {
            System.out.println("笔记异常：" + e);
        }
    }

    private void saveMybatisCode(String codeWarehouse) {
        File f = new File(codeWarehouse);
        String beginText = "\n> 文件夹:" + codeWarehouse + "\n";
        String endText = ("\n```\n</details>\n\n---\n");
        File[] fileName = f.listFiles();
        assert fileName != null;
        for (File fs : fileName) {
            if (fs.isFile()) {
                try {
                    String reallyName = fs.getName();
                    String endSuffix = reallyName.replaceAll(".+\\.", "");
                    String secondText = ("**" + reallyName + "**" + beginText + "\n\n<details> \n<summary>" + reallyName + "</summary>\n\n```" + endSuffix + "\n");
                    BufferedReader br01 = new BufferedReader(new FileReader(fs));
                    BufferedWriter bw01 = new BufferedWriter(new FileWriter(cloth, true));
                    bw01.write(secondText);
                    String tempt;
                    while ((tempt = br01.readLine()) != null) {
                        bw01.write(tempt);
                        bw01.newLine();
                    }
                    br01.close();
                    bw01.write(endText);
                    bw01.flush();
                    bw01.close();
                } catch (IOException e) {
                    System.out.println("文件异常：\n" + e);
                }
            }
            if (fs.isDirectory()) {
                saveMybatisCode(fs.getPath());
            }
        }
    }


    public String prospective() {
        saveMybatisCode("src/main");
        saveMybatisCode("src/test/java");
        return "笔记写入完成";
    }
}
