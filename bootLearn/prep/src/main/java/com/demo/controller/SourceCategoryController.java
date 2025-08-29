package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.SourceCategory;
import com.demo.model.vo.CateLabelVo;
import com.demo.model.vo.SourceCateVo;
import com.demo.service.ISourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 题源分类表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-08-29
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
        sourceCategory.setCreateDate(LocalDate.now());
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

    @GetMapping("/page")
    public IPage<SourceCateVo> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return sourceCategoryService.getByPage(pageNum, pageSize);
    }

    @GetMapping("/label")
    public List<CateLabelVo> getCateLabel() {
        return sourceCategoryService.getCateLabel();
    }

}
