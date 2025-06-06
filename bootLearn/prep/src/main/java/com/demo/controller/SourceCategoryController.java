package com.demo.controller;

import com.demo.common.Result;
import com.demo.model.entity.SourceCategory;
import com.demo.service.ISourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 题源分类表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/api/sourceCategory")
public class SourceCategoryController {
    private final ISourceCategoryService sourceCategoryService;

    @Autowired
    public SourceCategoryController(ISourceCategoryService sourceCategoryService) {
        this.sourceCategoryService = sourceCategoryService;
    }

    @PostMapping
    public Result add(@RequestBody SourceCategory sourceCategory) {
        boolean addRes = sourceCategoryService.save(sourceCategory);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = sourceCategoryService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody SourceCategory sourceCategory) {
        boolean updateRes = sourceCategoryService.updateById(sourceCategory);
        return Result.choice("修改", updateRes);
    }
}
