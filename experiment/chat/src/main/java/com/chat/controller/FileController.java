package com.chat.controller;

import com.chat.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 文件上传下载所调用的接口
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private static final String dirPath = Paths.get("").toAbsolutePath() + "\\chatFile\\";

    @PostMapping("/upload")
    public Result upload(MultipartFile myFile) {
        // 检查对应目录是否存在
        File directory = new File(dirPath);
        if (!directory.exists()) {
            boolean mkdir = directory.mkdir();
            log.info("创建文件夹：{}", mkdir ? "成功" : "失败");
        }
        // 开始转存
        String filePath = dirPath + myFile.getOriginalFilename();
        try {
            File newFile = new File(filePath);
            // 防止覆盖
            if (newFile.exists()) {
                log.info("已存在文件:{}", filePath);
                return new Result(false, "文件已存在");
            }
            myFile.transferTo(newFile);//转存临时文件
            log.info("文件转存至:{}", filePath);
            return new Result(true, "上传成功");
        } catch (IOException e) {
            log.warn("转存图片失败");
            return new Result(false, "上传失败");
        }
    }
}

