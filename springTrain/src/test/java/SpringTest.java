import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pojo.StudentsTable;
import service.StudentService;

import java.util.List;

public class SpringTest {
    @Test
    public void testMybatis(){
        AnnotationConfigApplicationContext acx = new AnnotationConfigApplicationContext(SpringConfig.class);
        StudentService service=acx.getBean(StudentService.class);
        List<StudentsTable> studentsTables=service.selectAll();
        for (StudentsTable studentsTable:studentsTables){
            System.out.println(studentsTable);
        }
    }

}
