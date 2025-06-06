package com.demo.controller;

import com.demo.common.Result;
import com.demo.model.entity.Subject;
import com.demo.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 科目表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final ISubjectService subjectService;

    @Autowired
    public SubjectController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public Result add(@RequestBody Subject subject) {
        boolean addRes = subjectService.save(subject);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = subjectService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody Subject subject) {
        boolean updateRes = subjectService.updateById(subject);
        return Result.choice("修改", updateRes);
    }
}
