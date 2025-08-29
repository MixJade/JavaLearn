package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.SourceImgDto;
import com.demo.model.entity.SourceImage;
import com.demo.service.ISourceImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

/**
 * <p>
 * 题源图片表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/api/sourceImage")
public class SourceImageController {
    private final ISourceImageService sourceImageService;

    @Autowired
    public SourceImageController(ISourceImageService sourceImageService) {
        this.sourceImageService = sourceImageService;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = sourceImageService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody SourceImage sourceImage) {
        boolean updateRes = sourceImageService.updateById(sourceImage);
        return Result.choice("修改", updateRes);
    }

    @PostMapping("/page")
    public IPage<SourceImage> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody SourceImgDto sourceImgDto) {
        return sourceImageService.getByPage(pageNum, pageSize, sourceImgDto);
    }

    @GetMapping("/{id}")
    public SourceImage detail(@PathVariable Integer id) {
        return sourceImageService.getById(id);
    }

    /**
     * 保存图片
     *
     * @param file   图片数据
     * @param sourceImage 图片备注+分类
     */
    @PostMapping("/uploadImg")
    public Result uploadImg(@RequestParam("file") MultipartFile file, @RequestBody SourceImage sourceImage) {
        return sourceImageService.saveImg(file, sourceImage);
    }

    /**
     * 识别图片文字
     */
    @GetMapping("/ocr/{id}")
    public Result ocr(@PathVariable Integer id) {
        return sourceImageService.ocr(id);
    }

    /**
     * 图源展示
     */
    @GetMapping("/img/{id}")
    public void img(@PathVariable Integer id, HttpServletResponse response) {
        if (id == 0) return; // 防止初始化的时候被调用
        String filePath = sourceImageService.getImgPath(id);
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
