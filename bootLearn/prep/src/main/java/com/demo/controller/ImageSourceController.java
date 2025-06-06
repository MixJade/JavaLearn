package com.demo.controller;

import com.demo.common.Result;
import com.demo.model.entity.ImageSource;
import com.demo.service.IImageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
