package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.ExamSubject;
import com.demo.service.IExamSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 科目表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
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
        return examSubjectService.addSubject(examSubject);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return examSubjectService.delSubject(id);
    }

    @PutMapping
    public Result update(@RequestBody ExamSubject examSubject) {
        return examSubjectService.updSubject(examSubject);
    }

    @GetMapping("/page")
    public IPage<ExamSubject> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return examSubjectService.getByPage(pageNum, pageSize);
    }
}
