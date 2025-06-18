package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.ExamPaperDto;
import com.demo.model.entity.ExamPaper;
import com.demo.service.IExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 试卷表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
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
        return examPaperService.addPaper(examPaper);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return examPaperService.delPaper(id);
    }

    @PutMapping
    public Result update(@RequestBody ExamPaper examPaper) {
        return examPaperService.updPaper(examPaper);
    }

    @PostMapping("/page")
    public IPage<ExamPaper> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody ExamPaperDto examPaperDto) {
        return examPaperService.getByPage(pageNum, pageSize, examPaperDto);
    }
}
