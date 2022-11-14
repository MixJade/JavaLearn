package com.demo;

import com.demo.dao.StudentsDao;
import com.demo.domain.Students;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BootDemoApplicationTests {
    @Autowired
    StudentsDao studentsDao;
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
}
