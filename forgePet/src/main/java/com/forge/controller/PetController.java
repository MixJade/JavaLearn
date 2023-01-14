package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.NameDto;
import com.forge.dto.Page;
import com.forge.dto.PetDto;
import com.forge.entity.Pet;
import com.forge.service.IPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 宠物信息表，外键用户表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private IPetService petService;

    @Autowired
    public void setPetService(IPetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<NameDto> getAll() {
        return petService.selectName();
    }

    @GetMapping("/client")
    public List<NameDto> getByClient(long clientId) {
        return petService.selectByClient(clientId);
    }

    @GetMapping("/noClient")
    public List<NameDto> getNoClient() {
        return petService.selectNoClient();
    }

    @GetMapping("/page")
    public Page<List<PetDto>> getPage(int numPage, int pageSize, String petName, String clientName) {
        return petService.selectByPage(petName, clientName, numPage, pageSize);
    }

    @GetMapping("/four")
    public List<PetDto> getFour() {
        return petService.selectFour();
    }

    @PostMapping
    public Result save(@RequestBody Pet pet) {
        return Result.choice("添加", petService.save(pet));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", petService.deleteById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Result deleteGroup(@PathVariable long[] ids) {
        return Result.choice("删除多个", petService.deleteByIds(ids));
    }

    @PutMapping
    public Result update(@RequestBody Pet pet) {
        return Result.choice("修改", petService.updatePet(pet));
    }
}
