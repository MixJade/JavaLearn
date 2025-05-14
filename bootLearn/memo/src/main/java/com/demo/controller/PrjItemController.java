package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.PrjPageDto;
import com.demo.model.entity.PrjItem;
import com.demo.model.vo.PrjItemVo;
import com.demo.service.IPrjItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 项目分类表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
@RestController
@RequestMapping("/api/prjItem")
public class PrjItemController {
    private final IPrjItemService prjItemService;

    @Autowired
    public PrjItemController(IPrjItemService prjItemService) {
        this.prjItemService = prjItemService;
    }

    @PostMapping
    public Result add(@RequestBody PrjItem prjItem) {
        boolean addRes = prjItemService.save(prjItem);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = prjItemService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody PrjItem prjItem) {
        boolean updateRes = prjItemService.updateById(prjItem);
        return Result.choice("修改", updateRes);
    }

    @PostMapping("/page")
    public IPage<PrjItemVo> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody PrjPageDto prjPageDto) {
        return prjItemService.getByPage(pageNum, pageSize, prjPageDto);
    }
}
