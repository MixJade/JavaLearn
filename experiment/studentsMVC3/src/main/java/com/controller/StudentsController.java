package com.controller;

import com.domain.Students;
import com.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    @Autowired
    StudentsService service;

    @PostMapping
    public String addStu(@RequestBody Students students) {
        System.out.println(students);
        service.addStu(students);
        return "{'module':'book save success'}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        System.out.println(id);
        service.deleteId(id);
        return "{'module':'book delete'}";
    }

    @PutMapping
    public String update(@RequestBody Students students){
        System.out.println(students);
        service.update(students);
        return "{'module':'book update'}";
    }

    @GetMapping
    public List<Students> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Students getById(@PathVariable Integer id) {
        return service.getById(id);
    }
}
