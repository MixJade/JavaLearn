package com.demo.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileUtil {
    /**
     * 递归删除文件夹及其所有子内容
     *
     * @param folder 要删除的文件夹路径
     */
    public static void deleteFolderRecursively(Path folder) {
        try (Stream<Path> walk = Files.walk(folder)) {
            walk.sorted((p1, p2) -> -p1.compareTo(p2))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("删除文件或文件夹 " + path + " 时出错: " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("删除文件夹时IO异常:" + e.getMessage());
        }
    }
}
