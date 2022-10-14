package operateFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileOutStreamAttempt {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 3; i++) {
                FileOutputStream fileAttempt = new FileOutputStream("August3rd_FileAttempt",true);
                byte[] b = ("TheWorld" + i + " ").getBytes();
                fileAttempt.write(b);
                fileAttempt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class attemptXmlMap {
        private final static String xmlPath = "src/javaOrigin/SaveCodeDictionary.xml";
        public static void main(String[] args) {
            makeNameMap();
        }


        private static void makeNameMap() {
            String strXml = " ";
            String fileName = "SaveCode.java";
            Map<String, ArrayList<String>> nameMap = new HashMap<>();
            try {
                strXml = Files.readString(Path.of(xmlPath));
            } catch (IOException e) {
                System.out.println("XML不见了");
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
                nameMap.put(m1.group(), followList);
                System.out.println(m1.group());
                System.out.println(nameMap.get(m1.group()).get(0));
                System.out.println(nameMap.get(m1.group()).get(1));
            }
        }
    }

    public static class ChangeSuffix {
        private static final String codeWarehouse = "C:\\MixJade\\Summer\\src\\helloWorld";

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
            File file = new File(codeWarehouse);
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
}
