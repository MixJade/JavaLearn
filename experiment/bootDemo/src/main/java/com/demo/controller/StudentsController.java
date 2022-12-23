package com.demo.controller;

import com.demo.common.Code;
import com.demo.common.Result;
import com.demo.dao.StudentsDao;
import com.demo.domain.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    StudentsDao studentsDao;

    @Autowired
    public void setStudentsDao(StudentsDao studentsDao) {
        this.studentsDao = studentsDao;
    }

    @PostMapping
    public Result addStu(@RequestBody Students students) {
        int addRes = studentsDao.insert(students);
        if (addRes > 0) {
            return new Result(Code.SAVE_OK, true, "添加成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "添加失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int deleteRes = studentsDao.deleteById(id);
        return new Result(deleteRes > 0 ? Code.DELETE_OK : Code.DELETE_ERR, deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody Students students) {
        int updateRes = studentsDao.updateById(students);
        return new Result(updateRes > 0 ? Code.UPDATE_OK : Code.UPDATE_ERR, updateRes, "修改");
    }

    @GetMapping()
    public Result getAll() {
        List<Students> students = studentsDao.selectList(null);
        if (students != null) {
            return new Result(Code.GET_OK, students, "查询成功");
        } else {
            return new Result(Code.GET_ERR, null, "查询失败");
        }
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Students student = studentsDao.selectById(id);
        if (student != null) {
            return new Result(Code.GET_OK, student, "查询成功");
        } else {
            return new Result(Code.GET_ERR, null, "查询失败，此人不存在");
        }
    }
}
