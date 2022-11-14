package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class MyController {
    @GetMapping("/{id}")
    public String getByID(@PathVariable Integer id) {
        System.out.println(id);
        return "{'info':'单个学生的信息'}";
    }

    @GetMapping
    public String getAll(){
        System.out.println("I am OK");
        return "{'info':'所有学生的信息'}";
    }
}