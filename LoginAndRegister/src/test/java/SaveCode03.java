import java.io.*;

public class SaveCode03 {
    public static void main(String[] args) {
        SaveCode03 saveCode03=new SaveCode03("C:\\Users\\11141\\Documents\\MarkMixJade\\JavaWeb笔记.md");
        System.out.println(saveCode03.prospective());
    }
    String cloth;

    SaveCode03(String cloth) {
        this.cloth = cloth;
        tideNote();
    }


    private void tideNote() {
        String cite = "# javaWeb笔记\n>创建时间: 2022年10月7日21点 \n>第一个web项目文件备份。\n---\n";
        try {
            FileWriter fileAttempt = new FileWriter(cloth);
            fileAttempt.write(cite);
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
        return "笔记写入完成";
    }
}
