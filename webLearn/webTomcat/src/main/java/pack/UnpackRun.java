package pack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 在打包时执行的脚本(在执行后，需去maven-package来将项目打成war包)
 *
 * @since 2025-05-05 01:01:55
 */
public class UnpackRun {
    public static void main(String[] args) {
        System.out.println("\n========MixJade自制打包脚本========");
        // 获取当前工作目录的绝对路径
        String absolutePath = System.getProperty("user.dir");
        // 打印绝对路径
        System.out.println("当前所在绝对路径: " + absolutePath);
        System.out.println("Tomcat所在绝对路径: " + PackConfig.TOMCAT_PATH);
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
        System.out.println("执行完毕，需去maven-package来将项目打成war包");
    }

    private static void writeServer(String directoryPath) {
        File serverFile = new File(directoryPath, "template/server.xml");
        try {
            String serverStr = Files.readString(serverFile.toPath());
            serverStr = serverStr.replace("C:\\MyCode\\JavaLearn\\webLearn\\webTomcat\\target", PackConfig.OUT_DIR)
                    .replace("7841", PackConfig.WEB_PORT);
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
            startBatStr = startBatStr.replace("C:\\MyCode\\JavaLearn\\webLearn\\webTomcat", PackConfig.OUT_DIR)
                    .replace("C:\\tomcat\\apache-tomcat-9.0.104", PackConfig.TOMCAT_PATH);
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
        File sourceFolder = new File(PackConfig.STATIC_PATH);

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
