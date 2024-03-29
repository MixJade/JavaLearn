package controller;

import domain.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.StudentsService;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    StudentsService service;

    @Autowired
    public void setService(StudentsService service) {
        this.service = service;
    }

    @PostMapping
    public Result addStu(@RequestBody Students students) {
        boolean addRes = service.addStu(students);
        System.out.println("添加操作，结果为" + addRes);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        System.out.println(id);
        boolean deleteRes = service.deleteId(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody Students students) {
        System.out.println(students);
        boolean updateRes = service.update(students);
        return Result.choice("修改", updateRes);
    }

    @GetMapping()
    public List<Students> getAll() {
        System.out.println("查询所有");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Students getById(@PathVariable Integer id) {
        return service.getById(id);

    }
}
