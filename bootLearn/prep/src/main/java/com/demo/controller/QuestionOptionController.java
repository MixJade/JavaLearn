package com.demo.controller;

import com.demo.common.Result;
import com.demo.model.entity.QuestionOption;
import com.demo.service.IQuestionOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 题目选项表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/api/questionOption")
public class QuestionOptionController {
    private final IQuestionOptionService questionOptionService;

    @Autowired
    public QuestionOptionController(IQuestionOptionService questionOptionService) {
        this.questionOptionService = questionOptionService;
    }

    @PostMapping
    public Result add(@RequestBody QuestionOption questionOption) {
        boolean addRes = questionOptionService.save(questionOption);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = questionOptionService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody QuestionOption questionOption) {
        boolean updateRes = questionOptionService.updateById(questionOption);
        return Result.choice("修改", updateRes);
    }
}
