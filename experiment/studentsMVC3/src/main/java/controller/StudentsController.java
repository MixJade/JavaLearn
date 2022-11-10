package controller;

import domain.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.StudentsService;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    @Autowired
    StudentsService service;

    @PostMapping
    public Result addStu(@RequestBody Students students) {
        System.out.println(students);
        boolean addRes = service.addStu(students);
        if (addRes){
            return new Result(Code.SAVE_OK, true,"添加成功");
        }else {
            return new Result(Code.SAVE_ERR, false,"添加失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        System.out.println(id);
        boolean deleteRes = service.deleteId(id);
        return new Result(deleteRes ? Code.DELETE_OK : Code.DELETE_ERR, deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody Students students) {
        System.out.println(students);
        boolean updateRes = service.update(students);
        return new Result(updateRes ? Code.UPDATE_OK : Code.UPDATE_ERR, updateRes);
    }

    @GetMapping()
    public Result getAll() {
        List<Students> students = service.getAll();
        if (students != null) {
            return new Result(Code.GET_OK, students,"查询成功");
        } else {
            return new Result(Code.GET_ERR, null, "查询失败");
        }
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Students student = service.getById(id);
        if (student != null) {
            return new Result(Code.GET_OK, student,"查询成功");
        } else {
            return new Result(Code.GET_ERR, null, "查询失败，此人不存在");
        }
    }
}
