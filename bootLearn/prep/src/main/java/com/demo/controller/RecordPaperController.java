package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.RecordPaper;
import com.demo.service.IRecordPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 试卷记录表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@RestController
@RequestMapping("/api/recordPaper")
public class RecordPaperController {
    private final IRecordPaperService recordPaperService;

    @Autowired
    public RecordPaperController(IRecordPaperService recordPaperService) {
        this.recordPaperService = recordPaperService;
    }

    @PostMapping
    public Result add(@RequestBody RecordPaper recordPaper) {
        boolean addRes = recordPaperService.save(recordPaper);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = recordPaperService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody RecordPaper recordPaper) {
        boolean updateRes = recordPaperService.updateById(recordPaper);
        return Result.choice("修改", updateRes);
    }

    @GetMapping("/page")
    public IPage<RecordPaper> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return recordPaperService.getByPage(pageNum, pageSize);
    }
}
