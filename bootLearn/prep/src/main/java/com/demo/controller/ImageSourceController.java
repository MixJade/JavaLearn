package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.ImgSourceDto;
import com.demo.model.entity.ImageSource;
import com.demo.model.vo.ImgSourceVo;
import com.demo.service.IImageSourceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.time.LocalDateTime;

/**
 * <p>
 * 题源图片表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/api/imageSource")
public class ImageSourceController {
    private final IImageSourceService imageSourceService;

    @Autowired
    public ImageSourceController(IImageSourceService imageSourceService) {
        this.imageSourceService = imageSourceService;
    }

    @PostMapping
    public Result add(@RequestBody ImageSource imageSource) {
        imageSource.setUploadTime(LocalDateTime.now());
        boolean addRes = imageSourceService.save(imageSource);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = imageSourceService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody ImageSource imageSource) {
        boolean updateRes = imageSourceService.updateById(imageSource);
        return Result.choice("修改", updateRes);
    }

    @PostMapping("/page")
    public IPage<ImgSourceVo> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody ImgSourceDto imgSourceDto) {
        return imageSourceService.getByPage(pageNum, pageSize, imgSourceDto);
    }

    @GetMapping("/{id}")
    public ImageSource detail(@PathVariable Integer id) {
        return imageSourceService.getById(id);
    }

    /**
     * 保存图片
     *
     * @param file   图片数据
     * @param cateId 文件夹主键
     */
    @PostMapping("/uploadImg")
    public Result uploadImg(@RequestParam("file") MultipartFile file, @RequestParam int cateId) {
        return imageSourceService.saveImg(file, cateId);
    }

    /**
     * 识别图片文字
     */
    @GetMapping("/ocr/{id}")
    public Result ocr(@PathVariable Integer id) {
        return imageSourceService.ocr(id);
    }

    /**
     * 图源展示
     */
    @GetMapping("/img/{id}")
    public void img(@PathVariable Integer id, HttpServletResponse response) {
        if (id == 0) return; // 防止初始化的时候被调用
        String filePath = imageSourceService.getImgPath(id);
        try (var fis = new FileInputStream(filePath); var ops = response.getOutputStream()) {
            response.setContentType("image/jpeg");
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1) {
                ops.write(bytes, 0, len);
                ops.flush();
            }
        } catch (Exception ignored) {
        }
    }
}
