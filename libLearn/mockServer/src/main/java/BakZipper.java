import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 将指定文件打包成zip到桌面
 *
 * @since 2026-03-11 09:24:19
 */
public class BakZipper {
    public static void main(String[] args) {
        try {
            // 要打包的目标文件、目录
            String javaFile = "src/main/java/MockDemo2.java";
            String resourceDir = "src/main/resources/mock2";
            // 输出的ZIP包名称
            String zipName = "mock2_package.zip";

            // 执行打包
            BakZipper.packToZip(zipName, javaFile, resourceDir);
        } catch (IOException e) {
            System.err.println("打包失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 核心压缩方法：递归添加文件/目录
     */
    private static void addToZip(File sourceFile, ZipOutputStream zos, String parentEntryName) throws IOException {
        String currentEntryName;
        if (parentEntryName.isEmpty()) {
            currentEntryName = sourceFile.getName();
        } else {
            currentEntryName = parentEntryName + "/" + sourceFile.getName();
        }

        if (sourceFile.isFile()) {
            // 1. 处理文件：创建文件条目并写入内容
            ZipEntry fileEntry = new ZipEntry(currentEntryName);
            // 设置文件元数据
            fileEntry.setSize(sourceFile.length());
            fileEntry.setTime(sourceFile.lastModified());
            zos.putNextEntry(fileEntry);

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile))) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bis.read(buffer)) != -1) {
                    zos.write(buffer, 0, bytesRead);
                }
            }
            zos.closeEntry();

        } else if (sourceFile.isDirectory()) {
            // 2. 处理目录
            // 强制给目录条目名称加/，这是ZIP识别目录的唯一标准
            String dirEntryName = currentEntryName.endsWith("/") ? currentEntryName : currentEntryName + "/";
            ZipEntry dirEntry = new ZipEntry(dirEntryName);
            // 设置目录元数据（大小为0，时间戳）
            dirEntry.setSize(0);
            dirEntry.setTime(sourceFile.lastModified());
            // 创建空目录
            zos.putNextEntry(dirEntry);
            zos.closeEntry();

            // 3. 递归处理子文件夹/文件
            File[] files = sourceFile.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    addToZip(childFile, zos, currentEntryName);
                }
            }
        }
    }

    /**
     * 打包指定文件和目录到ZIP
     *
     * @param zipName   生成的ZIP包路径
     * @param filePaths 要打包的文件/目录路径列表
     * @throws IOException IO异常
     */
    public static void packToZip(String zipName, String... filePaths) throws IOException {
        // 获取桌面路径
        File desktopDir = new File(System.getProperty("user.home") + "/Desktop");
        File zipFile = new File(desktopDir, zipName);

        // 初始化ZIP输出流
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)), StandardCharsets.UTF_8)) {
            for (String path : filePaths) {
                File source = new File(path);
                if (!source.exists()) {
                    throw new FileNotFoundException("文件/目录不存在：" + path);
                }
                addToZip(source, zos, "");
            }
        }
        System.out.println("压缩完成！ZIP包路径：" + zipFile.getAbsolutePath());
    }
}