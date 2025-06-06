package com.demo.controller;

import com.demo.common.Result;
import com.demo.model.entity.QuestionRecord;
import com.demo.service.IQuestionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 题目记录表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/api/questionRecord")
public class QuestionRecordController {
    private final IQuestionRecordService questionRecordService;

    @Autowired
    public QuestionRecordController(IQuestionRecordService questionRecordService) {
        this.questionRecordService = questionRecordService;
    }

    @PostMapping
    public Result add(@RequestBody QuestionRecord questionRecord) {
        boolean addRes = questionRecordService.save(questionRecord);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = questionRecordService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody QuestionRecord questionRecord) {
        boolean updateRes = questionRecordService.updateById(questionRecord);
        return Result.choice("修改", updateRes);
    }
}
