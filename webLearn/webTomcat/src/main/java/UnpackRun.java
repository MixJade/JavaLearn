import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 在打包时执行的脚本
 *
 * @since 2025-05-05 01:01:55
 */
public class UnpackRun {
    /**
     * 这里不允许修改(除了第一次运行)，后续请修改pack-local.iml
     */
    private static String tomcatPath = "C:/tomcat/apache-tomcat-9.0.104";
    private static String staticPath = "../../../TsLearn/ship-demo/dist";
    private static String outDir = "target"; // 最后war要运行的路径

    public static void main(String[] args) {
        System.out.println("\n========MixJade自制打包脚本========");
        // 获取当前工作目录的绝对路径
        String absolutePath = System.getProperty("user.dir");
        // 打印绝对路径
        System.out.println("当前所在绝对路径: " + absolutePath);
        if ("target".equals(outDir)) outDir = absolutePath + "\\" + outDir;
        handleLocalFile(absolutePath);
        System.out.println("Tomcat所在绝对路径: " + tomcatPath);
        // 检查是否执行过
        File checkFile = new File("target/ROOT/WEB-INF/web.xml");
        if (checkFile.exists()) {
            System.out.println("========当前脚本已执行,跳过========\n");
            return;
        }
        // 复制资源文件
        writeServer(absolutePath);
        writeStartBat(absolutePath);
        System.out.println("========开始复制资源========");
        copyWebXml();
        copyStatic();
    }

    /**
     * 处理本地配置文件
     *
     * @param directoryPath 当前java执行路径
     */
    private static void handleLocalFile(String directoryPath) {
        File localFile = new File(directoryPath, "pack-local.iml");
        try {
            if (localFile.exists()) {
                // 读取文件
                String strXml = Files.readString(localFile.toPath());
                Pattern p1 = Pattern.compile("(?<=<tomcatPath>).+(?=</tomcatPath>)");
                Pattern p2 = Pattern.compile("(?<=<staticPath>).+(?=</staticPath>)");
                Pattern p3 = Pattern.compile("(?<=<outDir>).+(?=</outDir>)");
                Matcher m1 = p1.matcher(strXml);
                Matcher m2 = p2.matcher(strXml);
                Matcher m3 = p3.matcher(strXml);
                while (m1.find() && m2.find() && m3.find()) {
                    tomcatPath = m1.group();
                    staticPath = m2.group();
                    outDir = m3.group();
                }
            } else {
                // 创建文件
                if (localFile.createNewFile()) {
                    // 写入配置
                    try (FileWriter fw = new FileWriter(localFile)) {
                        fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                                "\n<tomcatPath>" + tomcatPath + "</tomcatPath>" +
                                "\n<staticPath>" + staticPath + "</staticPath>" +
                                "\n<outDir>" + outDir + "</outDir>");
                    }
                    System.out.println("本地配置文件已创建：" + localFile.getAbsolutePath());
                } else {
                    System.out.println("无法创建文件。");
                }
            }
        } catch (IOException e) {
            System.out.println("发生 I/O 错误：" + e.getMessage());
        }
    }

    private static void writeServer(String directoryPath) {
        File serverFile = new File(directoryPath, "template/server.xml");
        try {
            String serverStr = Files.readString(serverFile.toPath());
            serverStr = serverStr.replace("C:\\MyCode\\JavaLearn\\webLearn\\webTomcat\\target", outDir);
            File newServerFile = new File(directoryPath, "target/server.xml");
            if (newServerFile.createNewFile()) {
                // 写入配置
                try (FileWriter fw = new FileWriter(newServerFile)) {
                    fw.write(serverStr);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeStartBat(String directoryPath) {
        File startBatFile = new File(directoryPath, "template/startWar.bat");
        try {
            String startBatStr = Files.readString(startBatFile.toPath());
            startBatStr = startBatStr.replace("C:\\MyCode\\JavaLearn\\webLearn\\webTomcat", outDir)
                    .replace("C:\\tomcat\\apache-tomcat-9.0.104", tomcatPath);
            File newServerFile = new File(directoryPath, "target/startWar.bat");
            if (newServerFile.createNewFile()) {
                // 写入配置
                try (FileWriter fw = new FileWriter(newServerFile)) {
                    fw.write(startBatStr);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制web.xml
     */
    public static void copyWebXml() {
        String sourceFilePath = "template/web.xml";
        String targetFilePath = "target/ROOT/WEB-INF/web.xml";
        // 创建文件夹
        File parentDir = (new File(targetFilePath)).getParentFile();

        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationPath = Paths.get(targetFilePath);
        try {
            if (parentDir.mkdirs()) {
                Files.copy(sourcePath, destinationPath);
                System.out.println("web.xml 复制成功");
            } else {
                System.err.println("target/ROOT/WEB-INF 目录已存在，请检查");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制静态资源
     */
    private static void copyStatic() {
        String resourcesPath = "target/ROOT";
        File sourceFolder = new File(staticPath);

        if (sourceFolder.exists() && sourceFolder.isDirectory()) {
            System.out.println("dist文件夹存在。");
            File destFolder = new File(resourcesPath);
            try {
                // 遍历dist下的所有子项并复制到static目录
                File[] filesToCopy = sourceFolder.listFiles();
                if (filesToCopy != null) {
                    for (File file : filesToCopy) {
                        copyFolder(file, new File(destFolder, file.getName()));
                    }
                    System.out.println("内容复制成功！");
                } else {
                    System.out.println("dist文件夹为空，无需复制。");
                }
            } catch (IOException e) {
                System.err.println("复制失败: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("dist文件夹不存在。");
        }
    }

    /**
     * 递归复制文件或目录
     *
     * @param source 源文件/目录
     * @param target 目标文件/目录
     * @throws IOException 复制过程中可能抛出的IO异常
     */
    private static void copyFolder(File source, File target) throws IOException {
        if (source.isDirectory()) {
            // 确保目标目录存在
            if (!target.exists() && !target.mkdirs()) {
                throw new IOException("无法创建目录: " + target.getAbsolutePath());
            }
            // 递归复制子项
            File[] subFiles = source.listFiles();
            if (subFiles == null) {
                throw new IOException("无法访问目录内容: " + source.getAbsolutePath());
            }
            for (File subFile : subFiles) {
                copyFolder(subFile, new File(target, subFile.getName()));
            }
        } else {
            // 确保目标文件的父目录存在
            File parentDir = target.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new IOException("无法创建目录: " + parentDir.getAbsolutePath());
            }
            // 复制文件（覆盖已存在的文件）
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
