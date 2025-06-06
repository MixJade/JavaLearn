package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
        return sourceCategoryService.saveCate(sourceCategory);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return sourceCategoryService.removeCate(id);
    }

    @PutMapping
    public Result update(@RequestBody SourceCategory sourceCategory) {
        return sourceCategoryService.updateCate(sourceCategory);
    }

    @GetMapping("/page")
    public IPage<SourceCategory> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return sourceCategoryService.getByPage(pageNum, pageSize);
    }
}
