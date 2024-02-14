import config.SpringConfig;
import domain.Students;
import service.StudentsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class StudentServiceTest {
    @Autowired
    private StudentsService service;

    @Test
    public void testMybatis() {
        List<Students> students = service.getAll();
        for (Students student : students) {
            System.out.println(student);
        }
    }
}