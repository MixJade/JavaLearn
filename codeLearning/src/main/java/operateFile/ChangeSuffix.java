package operateFile;

import java.io.File;
import java.util.Scanner;

public class ChangeSuffix {
    private static final String DIR = "src/main/java/helloWorld";

    public static void main(String[] args) {
        System.out.println("请输入1以更改文件后缀，输入2以将文件后缀换回正常:\n");
        Scanner checkIn = new Scanner(System.in);
        int check = checkIn.nextInt();
        checkFunction(check);
    }

    private static void checkFunction(int check) {
        if (check == 1) {
            replaceSuffix("java", "mixJava");
            replaceSuffix("xml", "mixXml");
        } else {
            replaceSuffix("mixJava", "java");
            replaceSuffix("mixXml", "xml");
        }
    }

    private static void replaceSuffix(String initialSuffix, String afterAltSuffix) {
        File file = new File(DIR);
        File[] files = file.listFiles();
        int fileNumber = 0;
        assert files != null;
        for (File subFile : files) {
            String name = subFile.getName();
            if (name.endsWith(initialSuffix)) {
                boolean resultSuffix = subFile.renameTo(new File(subFile.getParent() + "/" + name.substring(0, name.indexOf(initialSuffix)) + afterAltSuffix));
                if (resultSuffix) {
                    fileNumber++;
                }
            }
        }
        System.out.println("一共操作了: " + fileNumber + "个" + initialSuffix + "文件");
    }
}
