package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.AdoptDto;
import com.forge.dto.Page;
import com.forge.entity.Adopt;
import com.forge.service.IAdoptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 领养宠物订单 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2023-01-12
 */
@RestController
@RequestMapping("/adopt")
public class AdoptController {
    private IAdoptService adoptService;

    @Autowired
    public void setAdoptService(IAdoptService adoptService) {
        this.adoptService = adoptService;
    }

    @GetMapping
    public List<Adopt> getAll() {
        return adoptService.list();
    }

    @GetMapping("/page")
    public Page<List<AdoptDto>> getPage(int numPage, int pageSize, String adoptName) {
        return adoptService.selectByPage(adoptName, numPage, pageSize);
    }

    @PostMapping
    public Result save(@RequestBody Adopt adopt) {
        return Result.choice("添加", adoptService.save(adopt));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", adoptService.deleteById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Result deleteGroup(@PathVariable long[] ids) {
        return Result.choice("删除多个", adoptService.deleteByIds(ids));
    }

    @PutMapping
    public Result update(@RequestBody Adopt adopt) {
        return Result.choice("修改", adoptService.updateById(adopt));
    }
}
