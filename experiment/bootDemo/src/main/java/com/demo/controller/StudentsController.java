package com.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.common.Result;
import com.demo.dao.StudentsDao;
import com.demo.domain.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    private StudentsDao studentsDao;

    @Autowired
    public void setStudentsDao(StudentsDao studentsDao) {
        this.studentsDao = studentsDao;
    }

    @PostMapping
    public Result addStu(@RequestBody Students students) {
        int addRes = studentsDao.insert(students);
        return Result.choice("添加", addRes > 0);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int deleteRes = studentsDao.deleteById(id);
        return Result.choice("删除", deleteRes > 0);
    }

    @DeleteMapping("/batch/{ids}")
    public Result deleteGroup(@PathVariable Integer[] ids) {
        int deleteRes = studentsDao.deleteBatchIds(Arrays.asList(ids));
        return Result.choice("删除多个", deleteRes > 0);
    }

    @PutMapping
    public Result update(@RequestBody Students students) {
        int updateRes = studentsDao.updateById(students);
        return Result.choice("修改", updateRes > 0);
    }

    @GetMapping()
    public List<Students> getAll() {
        return studentsDao.selectList(null);
    }

    @GetMapping("/like/{stuName}")
    public List<Students> getLike(@PathVariable String stuName) {
        LambdaQueryWrapper<Students> lqw = new LambdaQueryWrapper<>();
        lqw.like(Students::getStudentName, stuName);// 模糊查询
        return studentsDao.selectList(lqw);
    }

    @GetMapping("/{id}")
    public Students getById(@PathVariable Integer id) {
        return studentsDao.selectById(id);
    }
}
