package com.demo.utils;

import com.demo.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class MyFileUtil {
    private static final Logger log = LoggerFactory.getLogger(MyFileUtil.class);
    @Value("${prep.dir}")
    private String prepDir;

    /**
     * 创建文件夹
     *
     * @param folderName 文件夹名称
     */
    public Result creatFolder(String folderName) {
        File sourceFolder = new File(prepDir + "\\" + folderName);
        if (sourceFolder.exists()) {
            log.error("文件夹：{} 已存在", folderName);
            return Result.error("文件夹已存在");
        } else {
            return Result.choice("创建文件夹", sourceFolder.mkdir());
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderName 文件夹名称
     */
    public void deleteFolder(String folderName) {
        File file = new File(prepDir + "\\" + folderName);
        if (file.exists()) {
            try (Stream<Path> walk = Files.walk(file.toPath())) {
                walk.sorted((p1, p2) -> -p1.compareTo(p2))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                log.error("删除文件或文件夹 " + path + " 时出错: " + e.getMessage());
                            }
                        });
            } catch (IOException e) {
                log.error("删除文件夹时IO异常:" + e.getMessage());
            }
        }
    }


    /**
     * 更改文件夹名称
     *
     * @param oldFolderName 旧文件夹名
     * @param folderName    新文件夹名
     */
    public Result updateFolderName(String oldFolderName, String folderName) {
        if (folderName == null || folderName.isBlank())
            return Result.error("文件夹名称不能为空");
        if (!oldFolderName.equals(folderName)) {
            File oldDir = new File(prepDir + "\\" + oldFolderName);
            if (oldDir.exists()) {
                File newDir = new File(prepDir + "\\" + folderName);
                boolean renameTo = oldDir.renameTo(newDir);
                if (!renameTo) return Result.error("文件夹更名失败");
            } else return Result.error("旧文件夹不存在，请确认");
        }
        return Result.suc("更名成功");
    }
}
