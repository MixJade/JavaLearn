package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.PaperRecord;
import com.demo.service.IPaperRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 试卷记录表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/api/paperRecord")
public class PaperRecordController {
    private final IPaperRecordService paperRecordService;

    @Autowired
    public PaperRecordController(IPaperRecordService paperRecordService) {
        this.paperRecordService = paperRecordService;
    }

    @PostMapping
    public Result add(@RequestBody PaperRecord paperRecord) {
        boolean addRes = paperRecordService.save(paperRecord);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = paperRecordService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody PaperRecord paperRecord) {
        boolean updateRes = paperRecordService.updateById(paperRecord);
        return Result.choice("修改", updateRes);
    }

    @GetMapping("/page")
    public IPage<PaperRecord> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return paperRecordService.getByPage(pageNum, pageSize);
    }
}
