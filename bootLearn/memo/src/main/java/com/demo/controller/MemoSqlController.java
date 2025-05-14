package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.MemoPageDto;
import com.demo.model.entity.MemoSql;
import com.demo.service.IMemoSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 备忘sql表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
@RestController
@RequestMapping("/api/memoSql")
public class MemoSqlController {
    private final IMemoSqlService memoSqlService;

    @Autowired
    public MemoSqlController(IMemoSqlService memoSqlService) {
        this.memoSqlService = memoSqlService;
    }

    @PostMapping
    public Result add(@RequestBody MemoSql memoSql) {
        boolean addRes = memoSqlService.save(memoSql);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = memoSqlService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody MemoSql memoSql) {
        boolean updateRes = memoSqlService.updateById(memoSql);
        return Result.choice("修改", updateRes);
    }

    @PostMapping("/page")
    public IPage<MemoSql> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody MemoPageDto memoPageDto) {
        return memoSqlService.getByPage(pageNum, pageSize, memoPageDto);
    }
}
