import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * 在打包之前,复制前端代码一起打包
 *
 * @since 2025-04-06 19:05:07
 */
public class BeforeBuild {
    public static void main(String[] args) {
        String distPath = "../../../TsLearn/payment-ui/dist";
        String resourcesPath = "src/main/resources/static";
        File sourceFolder = new File(distPath);

        if (sourceFolder.exists() && sourceFolder.isDirectory()) {
            System.out.println("dist文件夹存在。");
            File destFolder = new File(resourcesPath);

            // 确保目标目录存在
            if (!destFolder.exists() && !destFolder.mkdirs()) {
                System.err.println("无法创建目标目录: " + destFolder.getAbsolutePath());
                return;
            }

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