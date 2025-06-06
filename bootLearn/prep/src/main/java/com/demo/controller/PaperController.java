package com.demo.controller;

import com.demo.common.Result;
import com.demo.model.entity.Paper;
import com.demo.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 试卷表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/api/paper")
public class PaperController {
    private final IPaperService paperService;

    @Autowired
    public PaperController(IPaperService paperService) {
        this.paperService = paperService;
    }

    @PostMapping
    public Result add(@RequestBody Paper paper) {
        boolean addRes = paperService.save(paper);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = paperService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody Paper paper) {
        boolean updateRes = paperService.updateById(paper);
        return Result.choice("修改", updateRes);
    }
}
