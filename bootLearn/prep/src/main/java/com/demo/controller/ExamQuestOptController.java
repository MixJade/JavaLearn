package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.ExamQuestOpt;
import com.demo.service.IExamQuestOptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 题目选项表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@RestController
@RequestMapping("/api/examQuestOpt")
public class ExamQuestOptController {
    private final IExamQuestOptService examQuestOptService;

    @Autowired
    public ExamQuestOptController(IExamQuestOptService examQuestOptService) {
        this.examQuestOptService = examQuestOptService;
    }

    @PostMapping
    public Result add(@RequestBody ExamQuestOpt examQuestOpt) {
        boolean addRes = examQuestOptService.save(examQuestOpt);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = examQuestOptService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody ExamQuestOpt examQuestOpt) {
        boolean updateRes = examQuestOptService.updateById(examQuestOpt);
        return Result.choice("修改", updateRes);
    }

    @GetMapping("/page")
    public IPage<ExamQuestOpt> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return examQuestOptService.getByPage(pageNum, pageSize);
    }
}
