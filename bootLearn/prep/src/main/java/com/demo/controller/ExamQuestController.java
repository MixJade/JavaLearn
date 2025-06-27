package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.ExamQuestDto;
import com.demo.model.entity.ExamQuest;
import com.demo.model.vo.QuestImgListVo;
import com.demo.service.IExamQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 题目表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@RestController
@RequestMapping("/api/examQuest")
public class ExamQuestController {
    private final IExamQuestService examQuestService;

    @Autowired
    public ExamQuestController(IExamQuestService examQuestService) {
        this.examQuestService = examQuestService;
    }

    @PostMapping
    public Result add(@RequestBody ExamQuest examQuest) {
        boolean addRes = examQuestService.save(examQuest);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = examQuestService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody ExamQuest examQuest) {
        boolean updateRes = examQuestService.updateById(examQuest);
        return Result.choice("修改", updateRes);
    }

    @PostMapping("/page")
    public IPage<ExamQuest> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody ExamQuestDto questDto) {
        return examQuestService.getByPage(pageNum, pageSize, questDto);
    }

    @GetMapping("/cateImg")
    public QuestImgListVo getCateImg(@RequestParam Integer questId) {
        return examQuestService.getCateImg(questId);
    }
}
