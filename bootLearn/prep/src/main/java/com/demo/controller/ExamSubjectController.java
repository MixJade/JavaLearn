package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.ExamSubject;
import com.demo.service.IExamSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 * 科目表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-25
 */
@RestController
@RequestMapping("/api/examSubject")
public class ExamSubjectController {
    private final IExamSubjectService examSubjectService;

    @Autowired
    public ExamSubjectController(IExamSubjectService examSubjectService) {
        this.examSubjectService = examSubjectService;
    }

    @PostMapping
    public Result add(@RequestBody ExamSubject examSubject) {
        examSubject.setCreateDate(LocalDate.now());
        boolean addRes = examSubjectService.save(examSubject);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = examSubjectService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody ExamSubject examSubject) {
        boolean updateRes = examSubjectService.updateById(examSubject);
        return Result.choice("修改", updateRes);
    }

    @GetMapping("/page")
    public IPage<ExamSubject> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return examSubjectService.getByPage(pageNum, pageSize);
    }
}
