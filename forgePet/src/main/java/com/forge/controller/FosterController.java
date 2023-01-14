package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.FosterDto;
import com.forge.dto.Page;
import com.forge.entity.Foster;
import com.forge.service.IFosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 寄养表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2023-01-12
 */
@RestController
@RequestMapping("/foster")
public class FosterController {
    private IFosterService fosterService;

    @Autowired
    public void setFosterService(IFosterService fosterService) {
        this.fosterService = fosterService;
    }

    @GetMapping
    public List<Foster> getAll() {
        return fosterService.list();
    }

    @GetMapping("/page")
    public Page<List<FosterDto>> getPage(int numPage, int pageSize, String fosterName) {
        return fosterService.selectByPage(fosterName, numPage, pageSize);
    }

    @PostMapping
    public Result save(@RequestBody Foster foster) {
        return Result.choice("添加", fosterService.save(foster));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", fosterService.deleteById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Result deleteGroup(@PathVariable long[] ids) {
        return Result.choice("删除多个", fosterService.deleteByIds(ids));
    }

    @PutMapping
    public Result update(@RequestBody Foster foster) {
        return Result.choice("修改", fosterService.updateById(foster));
    }
}
