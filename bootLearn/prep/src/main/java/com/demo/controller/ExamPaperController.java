package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.ExamPaper;
import com.demo.model.vo.ExamPaperVo;
import com.demo.service.IExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 试卷表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-08-29
 */
@RestController
@RequestMapping("/api/examPaper")
public class ExamPaperController {
    private final IExamPaperService examPaperService;

    @Autowired
    public ExamPaperController(IExamPaperService examPaperService) {
        this.examPaperService = examPaperService;
    }

    @PostMapping
    public Result add(@RequestBody ExamPaper examPaper) {
        boolean addRes = examPaperService.save(examPaper);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = examPaperService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody ExamPaper examPaper) {
        boolean updateRes = examPaperService.updateById(examPaper);
        return Result.choice("修改", updateRes);
    }

    @GetMapping("/page")
    public IPage<ExamPaperVo> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return examPaperService.getByPage(pageNum, pageSize);
    }
}
