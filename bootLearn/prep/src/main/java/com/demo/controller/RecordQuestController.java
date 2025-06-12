package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.RecordQuest;
import com.demo.service.IRecordQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 题目记录表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@RestController
@RequestMapping("/api/recordQuest")
public class RecordQuestController {
    private final IRecordQuestService recordQuestService;

    @Autowired
    public RecordQuestController(IRecordQuestService recordQuestService) {
        this.recordQuestService = recordQuestService;
    }

    @PostMapping
    public Result add(@RequestBody RecordQuest recordQuest) {
        boolean addRes = recordQuestService.save(recordQuest);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = recordQuestService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody RecordQuest recordQuest) {
        boolean updateRes = recordQuestService.updateById(recordQuest);
        return Result.choice("修改", updateRes);
    }

    @GetMapping("/page")
    public IPage<RecordQuest> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return recordQuestService.getByPage(pageNum, pageSize);
    }
}
