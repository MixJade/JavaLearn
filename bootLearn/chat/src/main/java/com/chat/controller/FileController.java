package com.chat.controller;

import com.chat.pojo.FileInfo;
import com.chat.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传下载所调用的接口
 */
@RestController
@RequestMapping("/api/file")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private static final String dirPath = Paths.get("").toAbsolutePath() + "\\chatFile\\";

    /**
     * 上传文件
     *
     * @param myFile formData格式的文件
     * @return 上传成功
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile myFile) {
        if (myFile == null) return new Result(false, "请选择文件");
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

    /**
     * 下载文件
     *
     * @param filename 文件名
     * @return 下载文件流
     */
    @GetMapping("/down/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename) {
        String filePath = dirPath + filename;
        try {
            File file = new File(filePath);
            String fileLength = String.valueOf(file.length());
            log.info("下载{}  文件大小{}kb", filename, file.length() >> 10);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(fileInputStream);
            // 还需对文件名进行转码
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + encodeName)
                    .header(HttpHeaders.CONTENT_LENGTH, fileLength)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取可下载的文件列表
     *
     * @return chatFile的存在文件
     */
    @GetMapping("/getFileList")
    public List<FileInfo> getFileList() {
        List<FileInfo> fileInfos = new ArrayList<>();
        // 检查对应目录是否存在
        File directory = new File(dirPath);
        if (directory.exists()) {
            // 存在则返回文件名列表
            File[] listOfFiles = directory.listFiles();
            if (listOfFiles == null) return fileInfos;
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileInfos.add(FileInfo.bu(file.getName(), file.length()));
                }
            }
        }
        return fileInfos;
    }
}

