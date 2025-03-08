package com.demo;

import com.demo.mapper.DogMapper;
import com.demo.mapper.StudentsDao;
import com.demo.model.entity.Dog;
import com.demo.model.entity.Students;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BootDemoApplicationTests {
    @Autowired
    StudentsDao studentsDao;

    @Autowired
    DogMapper dogMapper;

    @Test
    void contextLoads() {
        System.out.println("测试类");
    }

    @Test
    void getALL(){
        List<Students> students = studentsDao.selectList(null);
        for (Students student:students){
            System.out.println(student);
        }
    }

    @Test
    void getALLDog(){
        // 通过MyBatis的collection标签循环查询
        List<Dog> dogs = dogMapper.queryAllDog();
        for (Dog dog : dogs) {
            System.out.println(dog);
        }
    }
}
