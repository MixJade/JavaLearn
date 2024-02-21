package com.demo.controller;

import com.demo.model.entity.Dog;
import com.demo.service.IDogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 狗 前端控制器
 *
 * @author MixJade
 * @since 2024-02-21
 */
@RestController
@RequestMapping("/dog")
public class DogController {
    private final IDogService dogService;

    @Autowired
    public DogController(IDogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping
    public List<Dog> getAll() {
        return dogService.queryAllDog();
    }
}
