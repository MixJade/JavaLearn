package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.ImgSourceDto;
import com.demo.model.entity.ImageSource;
import com.demo.service.IImageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public IPage<ImageSource> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody ImgSourceDto imgSourceDto) {
        return imageSourceService.getByPage(pageNum, pageSize, imgSourceDto);
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
     *
     * @param id   图片主键
     */
    @GetMapping("/{id}")
    public Result ocr(@PathVariable Integer id) {
        return imageSourceService.ocr(id);
    }
}
