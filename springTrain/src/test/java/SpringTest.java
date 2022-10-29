import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.StudentsTable;
import service.StudentService;
import testAOP.AOPTest;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class SpringTest {
    @Autowired
    private StudentService service;

    @Test
    public void testMybatis(){
        List<StudentsTable> studentsTables=service.selectAll();
        for (StudentsTable studentsTable:studentsTables){
            System.out.println(studentsTable);
        }
    }
    @Autowired
    private AOPTest aopTest;
    @Test
    public void testAOP(){
        aopTest.firstMethod();
    }
}
