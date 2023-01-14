package com.forge.controller;


import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
public class ImgController {
    private static final Logger log = LoggerFactory.getLogger(ImgController.class);
    @Value("${pet-forge.images-path}")
    private String basePath;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public String upload(MultipartFile myFile) {
        if (myFile == null) {
            log.warn("上传文件为空");
            return "文件上传为空";
        }
        String originalFilename = myFile.getOriginalFilename();//abc.jpg
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;//使用UUID重新生成文件名
        String filePath = basePath + fileName;
        try {
            myFile.transferTo(new File(filePath));//转存临时文件
            Thumbnails.of(filePath).forceSize(128, 128).toFile(filePath);//修改图片尺寸
        } catch (IOException e) {
            log.warn("转存文件失败");
        }
        return fileName;
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(basePath + name);
            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            log.info("未找到相应图片" + name);
        }
    }
}
